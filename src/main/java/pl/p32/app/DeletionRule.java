package pl.p32.app;

import java.util.regex.Pattern;

public class DeletionRule {

    private final boolean isDirectory;
    private final Pattern pattern;

    public boolean isDirectory() {
        return isDirectory;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public DeletionRule(String path) {
        isDirectory = path.endsWith("/");
        pattern = createPatternFromPath(path);
    }

    private Pattern createPatternFromPath(String path) {
        StringBuilder expression = new StringBuilder();
        for (int i = 0; i < path.length(); i++) {
            char currentChar = path.charAt(i);
            if (currentChar == '*')
                expression.append(".+");
            else if (currentChar == '.')
                expression.append("\\.");
            else if (currentChar == '/' && i != path.length() - 1)
                throw new IllegalArgumentException("Given path: " + path + " contains slash symbol \"/\" at illegal position");
            else if (currentChar != '/')
                expression.append(currentChar);
        }
        return Pattern.compile(expression.toString());
    }
}
