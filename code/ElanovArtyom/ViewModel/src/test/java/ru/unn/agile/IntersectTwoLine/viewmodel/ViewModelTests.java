package ru.unn.agile.IntersectTwoLine.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.IntersectTwoLine.viewmodel.ViewModel.Status;
import java.util.List;

import static org.junit.Assert.*;
import static ru.unn.agile.IntersectTwoLine.viewmodel.Regexmatchers.matchesPattern;


/**
 * Created by Artyom on 026 26.02.17.
 */
public class ViewModelTests {
    private ViewModel viewModel;

    public void setViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUp() {
        FakeLogger logger = new FakeLogger();
        viewModel = new ViewModel(logger);
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals("", viewModel.getA1());
        assertEquals("", viewModel.getB1());
        assertEquals("", viewModel.getC1());
        assertEquals("", viewModel.getA2());
        assertEquals("", viewModel.getB2());
        assertEquals("", viewModel.getC2());
        assertEquals("", viewModel.getResult());
        assertEquals(Status.WAITING, viewModel.getStatus());
    }

    @Test
    public void canSetBadFormatMessage() {
        viewModel.setA1("a");

        viewModel.checkIntersect();

        assertEquals(Status.BAD_FORMAT, viewModel.getStatus());
    }

    @Test
    public void isStatusWaitingInTheBeginning() {
        assertEquals(Status.WAITING, viewModel.getStatus());
    }

    @Test
    public void isStatusWaitingWhenCheckIntersectWithEmptyFields() {
        viewModel.checkIntersect();

        assertEquals(Status.WAITING, viewModel.getStatus());
    }

    @Test
    public void isStatusReadyWhenFieldsAreFill() {
        fillInputFields();

        viewModel.processKeyInTF(KeyboardKeys.ANY);

        assertEquals(Status.READY, viewModel.getStatus());
    }

    private void fillInputFields() {
        viewModel.setA1("1");
        viewModel.setB1("2");
        viewModel.setC1("3");
        viewModel.setA2("4");
        viewModel.setB2("5");
        viewModel.setC2("6");
    }

    @Test
    public void canReportBadFormat() {
        viewModel.setA1("a");
        viewModel.processKeyInTF(KeyboardKeys.ANY);

        assertEquals(Status.BAD_FORMAT, viewModel.getStatus());
    }

    @Test
    public void canCleanStatusIfParseIsOK() {
        viewModel.setA1("a");
        viewModel.processKeyInTF(KeyboardKeys.ANY);
        viewModel.setA1("1");
        viewModel.processKeyInTF(KeyboardKeys.ANY);

        assertEquals(Status.WAITING, viewModel.getStatus());
    }

    @Test
    public void canSetSuccessMessage() {
        fillInputFields();

        viewModel.checkIntersect();

        assertEquals(Status.SUCCESS, viewModel.getStatus());
    }

    @Test
    public void isCheckButtonDisabledInitially() {
        assertEquals(false, viewModel.isCalculateButtonEnabled());
    }

    @Test
    public void isCheckButtonDisabledWhenFormatIsBad() {
        fillInputFields();
        viewModel.processKeyInTF(KeyboardKeys.ANY);
        assertEquals(true, viewModel.isCalculateButtonEnabled());

        viewModel.setA1("a");
        viewModel.processKeyInTF(KeyboardKeys.ANY);

        assertEquals(false, viewModel.isCalculateButtonEnabled());
    }

    @Test
    public void isCheckButtonDisabledWithIncompleteInput() {
        viewModel.setA1("1");
        viewModel.setA2("2");

        viewModel.processKeyInTF(KeyboardKeys.ANY);

        assertEquals(false, viewModel.isCalculateButtonEnabled());
    }

    @Test
    public void isCheckButtonEnabledWithCorrectInput() {
        fillInputFields();

        viewModel.processKeyInTF(KeyboardKeys.ANY);

        assertEquals(true, viewModel.isCalculateButtonEnabled());
    }

    @Test
    public void canPerformCheckParallel() {
        viewModel.setA1("2.1");
        viewModel.setB1("3.1");
        viewModel.setC1("-1");
        viewModel.setA2("4.2");
        viewModel.setB2("6.2");
        viewModel.setC2("7");

        viewModel.checkIntersect();

        assertEquals("Lines are parallel", viewModel.getResult());
    }

    @Test
    public void canPerformCheckMatch() {
        viewModel.setA1("2.1");
        viewModel.setB1("3.1");
        viewModel.setC1("-1");
        viewModel.setA2("4.2");
        viewModel.setB2("6.2");
        viewModel.setC2("-2");

        viewModel.checkIntersect();

        assertEquals("Lines match", viewModel.getResult());
    }

    @Test
    public void canPerformCheckIntersectPoint() {
        viewModel.setA1("3.1");
        viewModel.setB1("-2.3");
        viewModel.setC1("3");
        viewModel.setA2("-4.5");
        viewModel.setB2("5.6");
        viewModel.setC2("-1");

        viewModel.checkIntersect();

        assertEquals("Intersection point ( 2.068473609129814 ; 1.4835948644793153 )",
                viewModel.getResult());
    }


    @Test
    public void isStatusReadyWhenKeyIsNotEnter() {
        fillInputFields();

        viewModel.processKeyInTF(KeyboardKeys.ANY);

        assertEquals(Status.READY, viewModel.getStatus());
    }

    @Test
    public void isStatusSuccessWhenKeyIsEnter() {
        fillInputFields();

        viewModel.processKeyInTF(KeyboardKeys.ENTER);

        assertEquals(Status.SUCCESS, viewModel.getStatus());
    }

    @Test
    public void canCreateViewModelWithLogger() {
        FakeLogger logger = new FakeLogger();
        ViewModel viewModelLogged = new ViewModel(logger);

        assertNotNull(viewModelLogged);
    }

    @Test
    public void viewModelConstructorWithNullLogger() {
        try {
            new ViewModel(null);
            fail("Exceptionn wasn't thrown");
        } catch (IllegalArgumentException ex) {
            assertEquals("Logger parameter can't be null", ex.getMessage());
        } catch (Exception ex) {
            fail("Invalidd exception type");
        }
    }

    @Test
    public void isLogEmptyInTheBeginning() {
        List<String> log = viewModel.getLogs();

        assertEquals(0, log.size());
    }

    @Test
    public void isCalculatePuttingSomething() {
        viewModel.checkIntersect();

        List<String> log = viewModel.getLogs();
        assertNotEquals(0, log.size());
    }

    @Test
    public void isLogContainsProperMessage() {
        viewModel.checkIntersect();
        String message = viewModel.getLogs().get(0);

        assertThat(message,
                matchesPattern(".*" + ViewModel.LogMessages.CALCULATE_WAS_PRESSED + ".*"));
    }

    @Test
    public void isLogContainsInputArguments() {
        fillInputFields();

        viewModel.checkIntersect();

        String message = viewModel.getLogs().get(0);
        assertThat(message, matchesPattern(".*" + viewModel.getA1()
                + ".*" + viewModel.getB1()
                + ".*" + viewModel.getC1()
                + ".*" + viewModel.getA2()
                + ".*" + viewModel.getB2()
                + ".*" + viewModel.getC2() + ".*"
        ));
    }

    @Test
    public void isProperlyFormattingInfoAboutArguments() {
        fillInputFields();

        viewModel.checkIntersect();
        String message = viewModel.getLogs().get(0);

        assertThat(message, matchesPattern(".*Arguments"
                + ": A1 = " + viewModel.getA1()
                + "; B1 = " + viewModel.getB1()
                + "; C1 = " + viewModel.getC1()
                + "; A2 = " + viewModel.getA2()
                + "; B2 = " + viewModel.getB2()
                + "; C2 = " + viewModel.getC2() + ".*"
        ));
    }

    @Test
    public void canPutSeveralLogMessages() {
        fillInputFields();

        viewModel.checkIntersect();
        viewModel.checkIntersect();
        viewModel.checkIntersect();

        assertEquals(3, viewModel.getLogs().size());
    }

    @Test
    public void isEditingFinishLogged() {
        viewModel.setA1("10");

        viewModel.focusLost();

        String message = viewModel.getLogs().get(0);
        assertThat(message, matchesPattern(".*" + ViewModel.LogMessages.EDITING_FINISHED + ".*"));
    }

    @Test
    public void areArgumentsCorrectlyLoggedOnEditingFinish() {
        fillInputFields();
        viewModel.focusLost();

        String message = viewModel.getLogs().get(0);
        assertThat(message, matchesPattern(".*" + ViewModel.LogMessages.EDITING_FINISHED
                + "Input arguments are: \\["
                + viewModel.getA1() + "; "
                + viewModel.getB1() + "; "
                + viewModel.getC1() + "; "
                + viewModel.getA2() + "; "
                + viewModel.getB2() + "; "
                + viewModel.getC2() + "\\]"));
    }

    @Test
    public void isLogInputsCalledOnEnter() {
        fillInputFields();

        viewModel.processKeyInTF(KeyboardKeys.ENTER);

        String message = viewModel.getLogs().get(0);
        assertThat(message, matchesPattern(".*" + ViewModel.LogMessages.EDITING_FINISHED + ".*"));
    }

    @Test
    public void isCalculateNotCalledWhenButtonIsDisabled() {
        viewModel.processKeyInTF(KeyboardKeys.ENTER);

        String logMessage = viewModel.getLogs().get(0);
        assertThat(logMessage, matchesPattern(".*"
                + ViewModel.LogMessages.EDITING_FINISHED + ".*"));
        assertEquals(1, viewModel.getLogs().size());
    }

    @Test
    public void doNotLogSameParametersTwice() {
        fillInputFields();
        fillInputFields();

        viewModel.focusLost();
        viewModel.focusLost();

        String message = viewModel.getLogs().get(0);
        assertThat(message, matchesPattern(".*" + ViewModel.LogMessages.EDITING_FINISHED + ".*"));
        assertEquals(1, viewModel.getLogs().size());
    }

    @Test
    public void doNotLogSameParametersTwiceWithPartialInput() {
        viewModel.setA1("123");
        viewModel.setA1("123");
        viewModel.setA1("123");

        viewModel.focusLost();
        viewModel.focusLost();
        viewModel.focusLost();

        assertEquals(1, viewModel.getLogs().size());
    }

}
