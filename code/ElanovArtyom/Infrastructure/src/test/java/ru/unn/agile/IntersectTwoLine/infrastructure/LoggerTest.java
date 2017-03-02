package ru.unn.agile.IntersectTwoLine.infrastructure;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.fail;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.unn.agile.IntersectTwoLine.viewmodel.Regexmatchers.matchesPattern;

/**
 * Created by Artyom on 001 01.03.17.
 */
public class LoggerTest {
    private static final String FILENAME = "./LoggerTest.logging";
    private Logger txtLogger;

    @Before
    public void setUp() {
        txtLogger = new Logger(FILENAME);
    }

    @Test
    public void canCreateLoggerAndSetFileName() {
        assertNotNull(txtLogger);
    }

    @Test
    public void canCreateLoggerFileOnHDD() {
        try {
            new BufferedReader(new FileReader(FILENAME));
        } catch (FileNotFoundException e) {
            fail("File " + FILENAME + " was not found!");
        }
    }

    @Test
    public void canWriteLogger() {
        String testMessage = "Test test test";

        txtLogger.logging(testMessage);

        String message = txtLogger.getLogs().get(0);
        assertThat(message, matchesPattern(".*" + testMessage + "$"));
    }

    @Test
    public void canWriteTwoLogMessages() {
        String[] logMessages = {"Test 1", "Test 2"};

        txtLogger.logging(logMessages[0]);
        txtLogger.logging(logMessages[1]);

        List<String> actualMessages = txtLogger.getLogs();
        for (int i = 0; i < actualMessages.size(); i++) {
            assertThat(actualMessages.get(i), matchesPattern(".*" + logMessages[i] + "$"));
        }
    }

    @Test
    public void doesSetLogDateAndTime() {
        String testMessage = "Test test test";

        txtLogger.logging(testMessage);

        String message = txtLogger.getLogs().get(0);
        assertThat(message, matchesPattern("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} > .*"));
    }
}
