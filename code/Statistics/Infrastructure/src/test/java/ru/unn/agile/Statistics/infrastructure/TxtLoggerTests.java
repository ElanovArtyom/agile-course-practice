package ru.unn.agile.Statistics.infrastructure;

import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.Statistics.infrastructure.TxtLogger;
import ru.unn.agile.Statistics.viewmodel.FakeLogger;
import ru.unn.agile.Statistics.viewmodel.LoggerTests;
import ru.unn.agile.Statistics.viewmodel.ViewModel;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class TxtLoggerTests extends LoggerTests {
    private static final String FILENAME = "./TxtLoggerTests.log";
    private TxtLogger txtLogger;

    @Before
    public void before() {
        txtLogger = new TxtLogger(FILENAME);
        vm = new ViewModel(txtLogger);
    }

    @Test
    public void canCreateLoggerWithFileName() {
        assertNotNull(txtLogger);
    }

    @Test
    public void canCreateLogFileOnDisk() {
        try {
            new BufferedReader(new FileReader(FILENAME));
        } catch (FileNotFoundException e) {
            fail("File " + FILENAME + " wasn't found!");
        }
    }

    @Test
    public void canWriteLogMessage() {
        String testMessage = "Test message";

        txtLogger.log(testMessage);

        String message = txtLogger.getLog().get(0);
        assertTrue(message.matches(".*" + testMessage + "$"));
    }

    @Test
    public void canWriteSeveralLogMessage() {
        String[] messages = {"Test message 1", "Test message 2"};

        txtLogger.log(messages[0]);
        txtLogger.log(messages[1]);

        List<String> actualMessages = txtLogger.getLog();
        for (int i = 0; i < actualMessages.size(); i++) {
            assertTrue(actualMessages.get(i).matches(".*" + messages[i] + "$"));
        }
    }

    @Test
    public void doesLogContainDateAndTime() {
        String testMessage = "Test message";

        txtLogger.log(testMessage);

        String message = txtLogger.getLog().get(0);
        assertTrue(message.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} > .*"));
    }
}
