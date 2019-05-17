package pl.p32.app;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class Cleaner {

    private final RuleMatcher matcher;
    private DeletionDetails deletionDetails;

    public DeletionDetails cleanDirectory(Path path) {
        if (!Files.isDirectory(path))
            throw new IllegalArgumentException("Provided path doesn't point to directory: " + path);
        deletionDetails = new DeletionDetails();
        clean(path);
        return deletionDetails;
    }

    private void clean(Path path) {
        try (Stream<Path> files = Files.list(path)) {
            files.forEach(file -> {
                if (matcher.matches(file))
                    deleteFileOrDirectory(file);
                else if (Files.isDirectory(file))
                    clean(file);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteFileOrDirectory(Path path) {
        try {
            if (Files.isDirectory(path)) {
                Stream<Path> files = Files.list(path);
                files.forEach(this::deleteFileOrDirectory);
                deletionDetails.addDirectory();
                files.close();
            } else {
                deletionDetails.addFile();
                deletionDetails.addBytes(Files.size(path));
            }

            if (!Files.isWritable(path)) {
                Files.setAttribute(path, "dos:readonly", false);
            }
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Cleaner of(Path rules) throws IOException {
        Stream<String> lines = Files.lines(rules);
        List<DeletionRule> deletionRules = lines
                .map(DeletionRule::of)
                .collect(Collectors.toList());
        lines.close();
        RuleMatcher matcher = new RuleMatcher(deletionRules);
        return new Cleaner(matcher);
    }
}
