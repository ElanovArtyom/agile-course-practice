package ru.unn.agile.SolvingQuadraticEquation.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import ru.unn.agile.SolvingQuadraticEquation.model.SolvingQuadraticEquation;

import java.util.ArrayList;
import java.util.List;

public class ViewModel {

    private static final int INFINITE_NUMBER_OF_SOLUTIONS = 3;

    private final StringProperty aProperty = new SimpleStringProperty();
    private final StringProperty bProperty = new SimpleStringProperty();
    private final StringProperty cProperty = new SimpleStringProperty();
    private final BooleanProperty solvingDisabled = new SimpleBooleanProperty();
    private final StringProperty resultProperty = new SimpleStringProperty();
    private final StringProperty statusProperty = new SimpleStringProperty();
    private final StringProperty log = new SimpleStringProperty();
    private final List<ValueChangeListener> valueChangedListeners = new ArrayList<>();
    private ILogger logger;

    public ViewModel() {
        init();
    }

    public final void setUpLogger(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter must be not null");
        }
        this.logger = logger;
    }

    public ViewModel(final ILogger logger) {
        setUpLogger(logger);
        init();
    }

    private void logUpdate() {
        List<String> fullLog = logger.getLog();
        String record = new String();
        for (String log : fullLog) {
            record += log + "\n";
        }
        log.set(record);
    }

    public final List<String> getLog() {
        return logger.getLog();
    }

    private void init() {
        aProperty.set("");
        bProperty.set("");
        cProperty.set("");
        resultProperty.set("");
        statusProperty.set(Status.WAITING.toString());
        BooleanBinding couldSolve = new BooleanBinding() {
            {
                super.bind(aProperty, bProperty, cProperty);
            }
            @Override
            protected boolean computeValue() {
                return getInputStatus() == Status.READY;
            }
        };
        solvingDisabled.bind(couldSolve.not());
        final List<StringProperty> fields = new ArrayList<StringProperty>() { {
            add(aProperty);
            add(bProperty);
            add(cProperty);
        }};

        for (StringProperty field : fields) {
            final ValueChangeListener listener = new ValueChangeListener();
            field.addListener(listener);
            valueChangedListeners.add(listener);
        }
    }


    public Property<String> aCoefProperty() {
        return aProperty;
    }
    public Property<String> bCoefProperty() {
        return bProperty;
    }
    public Property<String> cCoefProperty() {
        return cProperty;
    }
    public BooleanProperty solvingDisabledProperty() {
        return solvingDisabled;
    }
    public final boolean getSolvingDisabled() {
        return solvingDisabled.get();
    }
    public StringProperty logsProperty() {
        return log;
    }
    public final String getLogs() {
        return log.get();
    }
    public StringProperty resultProperty() {
        return resultProperty;
    }
    public final String getResult() {
        return resultProperty.get();
    }
    public StringProperty statusProperty() {
        return statusProperty;
    }
    public final String getStatus() {
        return statusProperty.get();
    }

    private Status getInputStatus() {
        Status inputStatus = Status.READY;
        if (aProperty.get().isEmpty() || bProperty.get().isEmpty()
                || cProperty.get().isEmpty()) {
            inputStatus = Status.WAITING;
        }
        try {
            if (!aProperty.get().isEmpty()) {
                Double.parseDouble(aProperty.get());
            }
            if (!bProperty.get().isEmpty()) {
                Double.parseDouble(bProperty.get());
            }
            if (!cProperty.get().isEmpty()) {
                Double.parseDouble(cProperty.get());
            }
        } catch (NumberFormatException nfe) {
            inputStatus = Status.BAD_FORMAT;
        }
        return inputStatus;
    }

    public void solve() {
        if (solvingDisabled.get()) {
            return;
        }
        double[] roots = SolvingQuadraticEquation
                .calc(aProperty.get(), bProperty.get(), cProperty.get());
        resultProperty.set(buildResultString(roots));
        statusProperty.set(Status.SUCCESS.toString());

        StringBuilder message = new StringBuilder(LogMessages.SOLVE_WAS_PRESSED);
        message.append("Arguments")
                .append(": a = ").append(aProperty.get())
                .append("; b = ").append(bProperty.get())
                .append("; c = ").append(cProperty.get()).append(".");
        logger.makeLog(message.toString());
        logUpdate();
    }

    public void onFocusFieldChanged(final Boolean prevValue, final Boolean nextValue) {
        if (!prevValue && nextValue) {
            return;
        }

        for (ValueChangeListener listener : valueChangedListeners) {
            if (listener.isChanged()) {
                StringBuilder message = new StringBuilder(LogMessages.INPUT_IN_FIELD_FINISHED);
                message.append("Input arguments are: [")
                        .append(aProperty.get()).append("; ")
                        .append(bProperty.get()).append("; ")
                        .append(cProperty.get()).append("]");
                logger.makeLog(message.toString());
                logUpdate();

                if (statusProperty.get().equals(Status.BAD_FORMAT.toString())) {
                    message = new StringBuilder("Error: ");
                    message.append(LogMessages.INCORRECT_INPUT);
                    logger.makeLog(message.toString());
                    logUpdate();
                }

                listener.cache();
                break;
            }
        }
    }

    private String buildResultString(final double[] roots) {
        switch (roots.length) {
            case 1:
                return "X = " + Double.toString(roots[0]);
            case 2:
                return "X(1) = " + Double.toString(roots[0]) + "; "
                        + "X(2) = " + Double.toString(roots[1]);
            case INFINITE_NUMBER_OF_SOLUTIONS:
                return "Infinite Set of Solutions";
            default:
                return "No Solutions";
        }
    }

    private class ValueChangeListener implements ChangeListener<String> {
        private String previousValue = new String();
        private String currentValue = new String();
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String prevValue, final String nextValue) {
            if (prevValue.equals(nextValue)) {
                return;
            }
            statusProperty.set(getInputStatus().toString());
            currentValue = nextValue;
        }
        public boolean isChanged() {
            return !previousValue.equals(currentValue);
        }
        public void cache() {
            previousValue = currentValue;
        }
    }
}

enum Status {
    WAITING("Please enter coefficients of Quadratic Equation"),
    READY("Press 'Solve' for solving Quadratic Equation"),
    BAD_FORMAT("Entered coefficients are incorrect!"),
    SUCCESS("Success");

    private final String name;
    Status(final String name) {
        this.name = name;
    }
    public String toString() {
        return name;
    }
}

final class LogMessages {
    public static final String SOLVE_WAS_PRESSED = "Calculate. ";
    public static final String INPUT_IN_FIELD_FINISHED = "Updated input. ";
    public static final String INCORRECT_INPUT = "Entered coefficients are incorrect! ";
    private LogMessages() { }
}
