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
    private static final String REPORT_TIME = " report for " + FORMATTER.format(LocalDateTime.now());
    private static final String FILE_PATH = "src/main/resources/";

    /**
     * I ran into a situation where I can't generate many reports for different files
     * and not destroy the ones finished if done within 1 minute. Therefore, I added
     * @param fileName to be able to avoid this
     */
    @Override
    public void writeToFile(String fileName, StringBuilder stringBuilder) {
        String currentReportName = FILE_PATH + fileName + REPORT_TIME;
        File currentReport = new File(currentReportName);
        if (!currentReport.exists()) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(
                    new FileWriter(currentReportName))) {
                bufferedWriter.write(String.valueOf(stringBuilder));
            } catch (IOException e) {
                throw new RuntimeException("Can't write to file " + currentReportName, e);
            }
        }
    }
}
