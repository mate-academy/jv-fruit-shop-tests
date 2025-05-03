package core.basesyntax.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvFileWriterTest {
    private static final String EXCEPTION_FIND_FILE_MESSAGE = "Can't find file by path: ";
    private static final String HEAD_REPORT = "fruit,quantity";
    private static final String FIRST_LINE_REPORT = "apple,140";
    private static final String SECOND_LINE_REPORT = "banana,100";
    private static final String INVALID_PATH = "src20/test/resources/report.csv";
    private static final String VALID_PATH = "src/test/resources/report.csv";
    private static WriterToFile writer;
    private List<String> report;

    @BeforeEach
    void setUp() {
        writer = new CsvFileWriter();
    }

    @Test
    void checkHeadReportExist_Ok() {
        report = List.of(
                HEAD_REPORT,
                FIRST_LINE_REPORT,
                SECOND_LINE_REPORT
        );
        assertTrue(report.contains(HEAD_REPORT));
    }

    @Test
    void reportNull_notOk() {
        assertThrows(RuntimeException.class,
                () -> writer.writeFile(null, INVALID_PATH));
    }

    @Test
    void invalidFilePath_notOk() {
        assertThrows(RuntimeException.class,
                () -> writer.writeFile(report, INVALID_PATH));
    }

    @Test
    void checkFirstLineReportExist_Ok() {
        report = List.of(
                HEAD_REPORT,
                FIRST_LINE_REPORT,
                SECOND_LINE_REPORT
        );
        assertTrue(report.contains(FIRST_LINE_REPORT));
    }

    @Test
    void checkSecondLineReportExist_Ok() {
        report = List.of(
                HEAD_REPORT,
                FIRST_LINE_REPORT,
                SECOND_LINE_REPORT
        );
        assertTrue(report.contains(FIRST_LINE_REPORT));
    }

    @Test
    void writeFile_Ok() {
        report = List.of(
                HEAD_REPORT,
                FIRST_LINE_REPORT,
                SECOND_LINE_REPORT
        );
        writer.writeFile(report, VALID_PATH);
        File file = new File(VALID_PATH);
        List<String> actualReport = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String value = reader.readLine();
            while (value != null) {
                actualReport.add(value);
                value = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(EXCEPTION_FIND_FILE_MESSAGE + VALID_PATH, e);
        }
        assertEquals(report, actualReport);
    }

    @AfterEach
    void tearDown() {
        File file = new File(VALID_PATH);
        if (file.exists()) {
            file.delete();
        }
    }
}
