package ru.unn.agile.matrixdiff.infrastructure;

import com.github.audice.matrixdiff.viewmodel.MatrixDiffViewModel;
import com.github.audice.matrixdiff.viewmodel.MatrixDiffViewModelTest;

import java.io.IOException;

/**
 * Created by Denis on 10.01.2017.
 */
public class TestOfViewModelWithRealLogger extends MatrixDiffViewModelTest {
    @Override
    public void enteringDate() {
        TextLogger realLogger =
                null;
        try {
            realLogger = new TextLogger("./ActionOfUser.log");
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.setCurViewModel(new MatrixDiffViewModel(realLogger));
    }
}
