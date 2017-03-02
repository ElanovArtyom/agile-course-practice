package ru.unn.agile.IntersectTwoLine.infrastructure;

import ru.unn.agile.IntersectTwoLine.viewmodel.ILogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


/**
 * Created by Artyom on 001 01.03.17.
 */
public class Logger implements ILogger {
        private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
        private final BufferedWriter bufferWriter;
        private final String nameOfFile;

    private static String nowAtThisDay() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW, Locale.ENGLISH);
        return sdf.format(calendar.getTime());
    }

    public Logger(final String filename) {
        this.nameOfFile = filename;

        BufferedWriter writenLog = null;
        try {
            writenLog = new BufferedWriter(new FileWriter(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
        bufferWriter = writenLog;
    }

    @Override
    public void logging(final String s) {
        try {
            bufferWriter.write(nowAtThisDay() + " > " + s);
            bufferWriter.newLine();
            bufferWriter.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getLogs() {
        BufferedReader reader;
        ArrayList<String> logs = new ArrayList<String>();
        try {
            reader = new BufferedReader(new FileReader(nameOfFile));
            String lines = reader.readLine();

            while (lines != null) {
                logs.add(lines);
                lines = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return logs;
    }

}

