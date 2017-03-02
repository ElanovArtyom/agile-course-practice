package ru.unn.agile.IntersectTwoLine.infrastructure;

import ru.unn.agile.IntersectTwoLine.viewmodel.ViewModel;
import ru.unn.agile.IntersectTwoLine.viewmodel.ViewModelTests;

/**
 * Created by Artyom on 001 01.03.17.
 */
public class ViewModelWithLoggerTest extends ViewModelTests {
    @Override
    public void setUp() {
        Logger realLogger =
                new Logger("./ViewModelWithLoggerTests.logging");
        super.setViewModel(new ViewModel(realLogger));
    }
}
