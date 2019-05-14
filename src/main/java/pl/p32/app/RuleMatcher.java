package pl.p32.app;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RuleMatcher {

    private final List<DeletionRule> deletionRules;

    public RuleMatcher(List<DeletionRule> deletionRules) {
        this.deletionRules = deletionRules;
    }

    public boolean matches(File file) {
        for (DeletionRule rule : deletionRules) {
            if (rule.isDirectory() == file.isDirectory()) {
                String fileName = file.getName();
                Pattern pattern = rule.getPattern();
                Matcher matcher = pattern.matcher(fileName);
                if (matcher.matches())
                    return true;
            }
        }
        return false;
    }
}
