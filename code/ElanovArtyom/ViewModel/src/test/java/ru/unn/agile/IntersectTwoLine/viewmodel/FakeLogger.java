package ru.unn.agile.IntersectTwoLine.viewmodel;

/**
 * Created by Artyom on 028 28.02.17.
 */
import java.util.ArrayList;
import java.util.List;

public class FakeLogger implements ILogger {
    private ArrayList<String> log = new ArrayList<String>();

    @Override
    public void logging(final String s) {
        log.add(s);
    }

    @Override
    public List<String> getLogs() {
        return log;
    }
}