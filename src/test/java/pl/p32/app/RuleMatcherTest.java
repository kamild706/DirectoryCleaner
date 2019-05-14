package pl.p32.app;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class RuleMatcherTest {

    @Test
    void shouldMatchGivenFile1() {
        DeletionRule rule = new DeletionRule("file.zip");
        RuleMatcher matcher = new RuleMatcher(Collections.singletonList(rule));
        File file = new File("C:\\Users\\Kamil\\file.zip");
        assertTrue(matcher.matches(file));
    }

    @Test
    void shouldMatchGivenFile2() {
        DeletionRule rule = new DeletionRule("*.zip");
        RuleMatcher matcher = new RuleMatcher(Collections.singletonList(rule));
        File file = new File("C:\\Users\\Kamil\\file.zip");
        assertTrue(matcher.matches(file));
    }

}