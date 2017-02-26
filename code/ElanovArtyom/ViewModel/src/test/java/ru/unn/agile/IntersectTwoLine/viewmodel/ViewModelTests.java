package ru.unn.agile.IntersectTwoLine.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.IntersectTwoLine.viewmodel.ViewModel.Status;

import static org.junit.Assert.*;

/**
 * Created by Artyom on 026 26.02.17.
 */
public class ViewModelTests {
    private ViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new ViewModel();
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

        viewModel.processKeyInTextField(KeyboardKeys.ANY);

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
        viewModel.processKeyInTextField(KeyboardKeys.ANY);

        assertEquals(Status.BAD_FORMAT, viewModel.getStatus());
    }

    @Test
    public void canCleanStatusIfParseIsOK() {
        viewModel.setA1("a");
        viewModel.processKeyInTextField(KeyboardKeys.ANY);
        viewModel.setA1("1");
        viewModel.processKeyInTextField(KeyboardKeys.ANY);

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
        viewModel.processKeyInTextField(KeyboardKeys.ANY);
        assertEquals(true, viewModel.isCalculateButtonEnabled());

        viewModel.setA1("a");
        viewModel.processKeyInTextField(KeyboardKeys.ANY);

        assertEquals(false, viewModel.isCalculateButtonEnabled());
    }

    @Test
    public void isCheckButtonDisabledWithIncompleteInput() {
        viewModel.setA1("1");
        viewModel.setA2("2");

        viewModel.processKeyInTextField(KeyboardKeys.ANY);

        assertEquals(false, viewModel.isCalculateButtonEnabled());
    }

    @Test
    public void isCheckButtonEnabledWithCorrectInput() {
        fillInputFields();

        viewModel.processKeyInTextField(KeyboardKeys.ANY);

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

        viewModel.processKeyInTextField(KeyboardKeys.ANY);

        assertEquals(Status.READY, viewModel.getStatus());
    }

    @Test
    public void isStatusSuccessWhenKeyIsEnter() {
        fillInputFields();

        viewModel.processKeyInTextField(KeyboardKeys.ENTER);

        assertEquals(Status.SUCCESS, viewModel.getStatus());
    }

}
