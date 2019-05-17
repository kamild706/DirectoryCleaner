package pl.p32.app;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RuleMatcherTest {

    @Test
    void shouldMatchGivenFile1() {
        DeletionRule rule = DeletionRule.of("file.zip");
        RuleMatcher matcher = new RuleMatcher(Collections.singletonList(rule));
        Path path = Paths.get("C:\\Users\\Kamil\\file.zip");
        assertTrue(matcher.matches(path));
    }

    @Test
    void shouldMatchGivenFile2() {
        DeletionRule rule = DeletionRule.of("*.zip");
        RuleMatcher matcher = new RuleMatcher(Collections.singletonList(rule));
        Path path = Paths.get("C:\\Users\\Kamil\\file.zip");
        assertTrue(matcher.matches(path));
    }

}