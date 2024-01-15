package core.basesyntax.services.fileprocessing.impl;

import core.basesyntax.services.fileprocessing.Writer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class WriterImpl implements Writer {
    private static final String DATE_TIME_PATTERN = "dd.MM.yy HH-mm";
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern(DATE_TIME_PATTERN, Locale.ENGLISH);
    private static final String REPORT_NAME = "REPORT FOR " + FORMATTER.format(LocalDateTime.now());
    private static final String FILE_PATH = "src/main/resources/" + REPORT_NAME;
    private static String currentReportName = FILE_PATH;
    private static int fileCounter = 1;

    @Override
    public void writeToFile(StringBuilder stringBuilder) {
        File currentReport = new File(currentReportName);
        if (!currentReport.exists()) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(
                    new FileWriter(currentReportName))) {
                bufferedWriter.write(String.valueOf(stringBuilder));
            } catch (IOException e) {
                throw new RuntimeException("Can't write to file " + currentReportName, e);
            }
        } else {
            currentReportName = FILE_PATH + " (" + fileCounter + ")";
            fileCounter++;
            writeToFile(stringBuilder);
        }
    }
}
