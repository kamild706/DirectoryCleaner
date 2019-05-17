package pl.p32.app;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.regex.Pattern;

@AllArgsConstructor
@Getter
public class DeletionRule {

    private final boolean isDirectory;
    private final Pattern pattern;

    public static DeletionRule of(String rule) {
        boolean isDirectory = rule.endsWith("/");
        Pattern pattern = createPatternForRule(rule);

        return new DeletionRule(isDirectory, pattern);
    }

    private static Pattern createPatternForRule(String rule) {
        StringBuilder expression = new StringBuilder();
        expression.append('^');
        for (int i = 0; i < rule.length(); i++) {
            char currentChar = rule.charAt(i);
            if (currentChar == '*')
                expression.append(".+");
            else if (currentChar == '.')
                expression.append("\\.");
            else if (currentChar == '/' && i != rule.length() - 1)
                throw new IllegalArgumentException("Given rule: " + rule + " contains slash symbol \"/\" at illegal position");
            else if (currentChar != '/')
                expression.append(currentChar);
        }
        expression.append('$');
        return Pattern.compile(expression.toString());
    }
}
