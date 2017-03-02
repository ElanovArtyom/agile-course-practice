package ru.unn.agile.IntersectTwoLine.viewmodel;

/**
 * Created by Artyom on 028 28.02.17.
 */
import java.util.List;

public interface ILogger {
    void logging(String s);

    List<String> getLogs();
}
