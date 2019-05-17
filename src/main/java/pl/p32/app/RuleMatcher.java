package pl.p32.app;

import lombok.AllArgsConstructor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
public class RuleMatcher {

    private final List<DeletionRule> deletionRules;

    public boolean matches(Path path) {
        for (DeletionRule rule : deletionRules) {
            if (rule.isDirectory() == Files.isDirectory(path)) {
                String fileName = path.getFileName().toString();
                Pattern pattern = rule.getPattern();
                Matcher matcher = pattern.matcher(fileName);
                if (matcher.matches())
                    return true;
            }
        }
        return false;
    }
}
