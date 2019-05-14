package pl.p32.app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    private static DeletionDetails deletionDetails = new DeletionDetails();

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("This program requires two arguments");
        }

        String directory = args[0];
        String configFile = args[1];

        Stream<String> lines = readContentFromFile(configFile);
        List<DeletionRule> deletionRules = createDeletionRules(lines);
        cleanDirectory(new File(directory), new RuleMatcher(deletionRules));
        System.out.println(deletionDetails);
    }

    private static void deleteFileOrDirectory(File path) {
        File[] files = path.listFiles();
        if (files != null) {
            for (File file : files) {
                deleteFileOrDirectory(file);
            }
        }

        if (path.isDirectory()) {
            deletionDetails.addDirectory();
        } else {
            deletionDetails.addFile();
            deletionDetails.addBytes(path.length());
        }

        path.delete();
    }

    private static void cleanDirectory(File directory, RuleMatcher matcher) {
        File[] files = directory.listFiles();
        if (files != null)
            for (File file : files) {
                if (matcher.matches(file)) {
                    deleteFileOrDirectory(file);
                } else if (file.isDirectory()) {
                    cleanDirectory(file, matcher);
                }
            }
    }

    private static List<DeletionRule> createDeletionRules(Stream<String> lines) {
        List<DeletionRule> deletionRules = new ArrayList<>();
        lines.forEach(line -> {
            DeletionRule rule = new DeletionRule(line);
            deletionRules.add(rule);
        });

        return deletionRules;
    }

    private static Stream<String> readContentFromFile(String file) {
        Path path = Paths.get(file);
        try  {
            return Files.lines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
