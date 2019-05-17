package pl.p32.app;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class DeletionRuleTest {

    @Test
    void deletionRuleShouldMatchGivenPath1() {
        String path = "node-modules/";
        DeletionRule rule = DeletionRule.of(path);
        Pattern pattern = Pattern.compile("^node-modules$");
        assertEquals(pattern.pattern(), rule.getPattern().pattern());
        assertTrue(rule.isDirectory());
    }

    @Test
    void deletionRuleShouldMatchGivenPath2() {
        String path = "*.v";
        DeletionRule rule = DeletionRule.of(path);
        Pattern pattern = Pattern.compile("^.+\\.v$");
        assertEquals(pattern.pattern(), rule.getPattern().pattern());
        assertFalse(rule.isDirectory());
    }

    @Test
    void deletionRuleShouldMatchGivenPath3() {
        String path = "file.zip";
        DeletionRule rule = DeletionRule.of(path);
        Pattern pattern = Pattern.compile("^file\\.zip$");
        assertEquals(pattern.pattern(), rule.getPattern().pattern());
        assertFalse(rule.isDirectory());
    }

    @Test
    void deletionRuleShouldThrowExceptionForInvalidSlashCharacter() {
        String path = "dir/ectory/";
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class, () -> DeletionRule.of(path), "Expected exception but it was not thrown");
        assertTrue(thrown.getMessage().contains("slash"));
    }
}