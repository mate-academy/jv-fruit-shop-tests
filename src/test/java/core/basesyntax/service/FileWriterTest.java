package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileWriterTest {
    private static final FileWriterToolImpl fileWriter = new FileWriterToolImpl();
    private static final String PATH_FOR_REPORT = "src/test/resources/report/report.csv";
    private static final String EMPTY_REPORT = "";
    private static final String HEADLINE = "fruit,quantity";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String FIRST_LINE = "apple,42";
    private static final String SECOND_LINE = "watermelon,69";
    private static final String SINGLE_FRUIT_REPORT =
            String.format(HEADLINE + LINE_SEPARATOR + FIRST_LINE);
    private static final String MULTIPLE_FRUIT_REPORT =
            String.format(HEADLINE + LINE_SEPARATOR
                    + FIRST_LINE + LINE_SEPARATOR + SECOND_LINE);
    private static final File report = new File(PATH_FOR_REPORT);
    private static final List<String> EMPTY_REPORT_LINES;
    private static final List<String> HEADLINE_REPORT_LINES;
    private static final List<String> CORRECT_SINGLE_FRUIT_REPORT_LINES;
    private static final List<String> CORRECT_MULTIPLE_FRUITS_REPORT_LINES;

    static {
        HEADLINE_REPORT_LINES = new ArrayList<>();
        HEADLINE_REPORT_LINES.add(HEADLINE);
        CORRECT_SINGLE_FRUIT_REPORT_LINES = new ArrayList<>();
        CORRECT_SINGLE_FRUIT_REPORT_LINES.add(HEADLINE);
        CORRECT_SINGLE_FRUIT_REPORT_LINES.add(FIRST_LINE);
        CORRECT_MULTIPLE_FRUITS_REPORT_LINES = new ArrayList<>();
        CORRECT_MULTIPLE_FRUITS_REPORT_LINES.addAll(CORRECT_SINGLE_FRUIT_REPORT_LINES);
        CORRECT_MULTIPLE_FRUITS_REPORT_LINES.add(SECOND_LINE);
        EMPTY_REPORT_LINES = Collections.emptyList();
    }

    @BeforeEach
    public void deleteReport() {
        report.delete();
    }

    @Test
    public void fileWriter_singleFruitToNewReportFile_Ok() throws IOException {
        fileWriter.writeToFile(SINGLE_FRUIT_REPORT, PATH_FOR_REPORT);

        assertEquals(Files.readAllLines(Path.of(PATH_FOR_REPORT)),
                CORRECT_SINGLE_FRUIT_REPORT_LINES);
    }

    @Test
    public void fileWriter_EmptyReportToNewReportFile_Ok() throws IOException {
        fileWriter.writeToFile(EMPTY_REPORT, PATH_FOR_REPORT);

        assertEquals(Files.readAllLines(Path.of(PATH_FOR_REPORT)), EMPTY_REPORT_LINES);
    }

    @Test
    public void fileWriter_headlineReportToNewReportFile_Ok() throws IOException {
        fileWriter.writeToFile(HEADLINE, PATH_FOR_REPORT);

        assertEquals(Files.readAllLines(Path.of(PATH_FOR_REPORT)), HEADLINE_REPORT_LINES);
    }

    @Test
    public void fileWriter_MultipleFruitReportToNewFile_Ok() throws IOException {
        fileWriter.writeToFile(MULTIPLE_FRUIT_REPORT, PATH_FOR_REPORT);

        assertEquals(Files.readAllLines(Path.of(PATH_FOR_REPORT)),
                CORRECT_MULTIPLE_FRUITS_REPORT_LINES);
    }

    @Test
    public void fileWriter_SingleFruitReportToFolderWithReportExisting_OK() throws IOException {
        report.createNewFile();
        assertTrue(report.exists());

        fileWriter.writeToFile(SINGLE_FRUIT_REPORT, PATH_FOR_REPORT);

        assertEquals(Files.readAllLines(Path.of(PATH_FOR_REPORT)),
                CORRECT_SINGLE_FRUIT_REPORT_LINES);
    }

}
