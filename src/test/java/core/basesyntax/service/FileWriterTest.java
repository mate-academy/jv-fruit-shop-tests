package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileWriterTest {
    private static final FileWriterToolImpl fileWriter = new FileWriterToolImpl();
    private static final String PATH_FOR_REPORT = "src/test/resources/report/report.csv";
    private static final String EMPTY_REPORT_PATH = "src/test/resources/report/emptyReport.csv";
    private static final String HEADLINE_REPORT_PATH =
            "src/test/resources/report/headLineReport.csv";
    private static final String CORRECT_SINGLE_FRUIT_REPORT_PATH =
            "src/test/resources/report/singleFruitReport.csv";
    private static final String CORRECT_MULTIPLE_FRUITS_REPORT_PATH =
            "src/test/resources/report/multipleFruitsReport.csv";
    private static final String EMPTY_REPORT = "";
    private static final String HEADLINE = "fruit,quantity";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String SINGLE_FRUIT_REPORT =
            String.format(HEADLINE + LINE_SEPARATOR + "apple,42");
    private static final String MULTIPLE_FRUIT_REPORT =
            String.format(HEADLINE + LINE_SEPARATOR
                    + "apple,42" + LINE_SEPARATOR + "watermelon,69");
    private static final File report = new File(PATH_FOR_REPORT);

    @BeforeEach
    public void deleteReport() {
        report.delete();
    }

    @Test
    public void writeSingleFruitReportToNewReportFile() throws IOException {
        fileWriter.writeToFile(SINGLE_FRUIT_REPORT, PATH_FOR_REPORT);
        assertEquals(Files.readAllLines(Path.of(PATH_FOR_REPORT)),
                Files.readAllLines(Path.of(CORRECT_SINGLE_FRUIT_REPORT_PATH)));
    }

    @Test
    public void writeEmptyReportToNewReportFile() throws IOException {
        fileWriter.writeToFile(EMPTY_REPORT, PATH_FOR_REPORT);
        assertEquals(Files.readAllLines(Path.of(PATH_FOR_REPORT)),
                Files.readAllLines(Path.of(EMPTY_REPORT_PATH)));
    }

    @Test
    public void writeHeadlineReportToNewReportFile() throws IOException {
        fileWriter.writeToFile(HEADLINE, PATH_FOR_REPORT);
        assertEquals(Files.readAllLines(Path.of(PATH_FOR_REPORT)),
                Files.readAllLines(Path.of(HEADLINE_REPORT_PATH)));
    }

    @Test
    public void writeMultipleFruitReportToNewFile() throws IOException {
        fileWriter.writeToFile(MULTIPLE_FRUIT_REPORT, PATH_FOR_REPORT);
        assertEquals(Files.readAllLines(Path.of(PATH_FOR_REPORT)),
                Files.readAllLines(Path.of(CORRECT_MULTIPLE_FRUITS_REPORT_PATH)));
    }

    @Test
    public void writeSingleFruitReportToFolderWithReportExisting() throws IOException {
        report.createNewFile();
        assertTrue(report.exists());
        fileWriter.writeToFile(SINGLE_FRUIT_REPORT, PATH_FOR_REPORT);
        assertEquals(Files.readAllLines(Path.of(PATH_FOR_REPORT)),
                Files.readAllLines(Path.of(CORRECT_SINGLE_FRUIT_REPORT_PATH)));
    }

}
