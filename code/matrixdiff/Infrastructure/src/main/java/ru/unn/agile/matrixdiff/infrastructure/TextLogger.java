package ru.unn.agile.matrixdiff.infrastructure;

import com.github.audice.matrixdiff.viewmodel.ILogger;

import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TextLogger implements ILogger {
    private static final String DEFAULT_FILENAME = "./ActionOfUser.log";
    private static final String DATE_STAMP = "yyyy-MM-dd HH:mm:ss";
    private final File currentFile;

    public TextLogger(final String filename) throws IOException {
        currentFile = new File(filename);
            FileWriter fstream = new FileWriter(filename);
            BufferedWriter outCurrentBuffer = new BufferedWriter(fstream);
            outCurrentBuffer.write("");
            outCurrentBuffer.close();
    }

    public TextLogger() throws IOException {
      this(DEFAULT_FILENAME);
    }

    private String timeMark() {
        return
         new SimpleDateFormat(DATE_STAMP, Locale.ENGLISH).format(Calendar.getInstance().getTime());
    }

    @Override
    public void log(final String message) {
        if (currentFile.isFile()) {
            try {
                FileWriter writer = new FileWriter(currentFile.getPath(), true);
                writer.append(timeMark() + " > " + message + System.getProperty("line.separator"));
                writer.flush();
                writer.close();
            } catch (IOException e) {
                //
            }
        }
    }

    @Override
    public List<String> getLog() {
        List<String> out = new ArrayList<>();
        if (currentFile.isFile()) {
            try {
                out = Files.readAllLines(currentFile.toPath());
            } catch (IOException e) {
                //
            }
        }
        return out;
    }
}
