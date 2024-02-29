package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.Writer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class FileWriterCsvImplTest {
    private static final String FILE_NAME = "src/test/resources/writer/reportCsv.csv";
    private static final Writer fileWriterCsv = new FileWriterCsvImpl();
    private static List<String> report;

    @BeforeAll
    static void beforeAll() {
        report = List.of(
            "apple,100",
            "orange,405",
            "lemon,100",
            "peach,623",
            "pear,40",
            "pineapple,86");
    }

    @Test
    void write_nullParameters_notOk() {
        String nullName = null;
        List<String> nullList = null;
        Throwable exception1 = assertThrows(IllegalArgumentException.class,
                () -> fileWriterCsv.write(nullName, nullList));
        assertEquals("""
                    Parameters can't be null, but:"
                    fileName = '%s'
                    report = '%s'""".formatted(nullName, nullList), exception1.getMessage());
        Throwable exception2 = assertThrows(IllegalArgumentException.class,
                () -> fileWriterCsv.write(FILE_NAME, nullList));
        assertEquals("""
                    Parameters can't be null, but:"
                    fileName = '%s'
                    report = '%s'""".formatted(FILE_NAME, nullList), exception2.getMessage());
        Throwable exception3 = assertThrows(IllegalArgumentException.class,
                () -> fileWriterCsv.write(nullName, report));
        assertEquals("""
                    Parameters can't be null, but:"
                    fileName = '%s'
                    report = '%s'""".formatted(nullName, report), exception3.getMessage());
    }

    @Test
    void write_reportIsEmpty_notOk() {
        List<String> emptyReport = new ArrayList<>();
        Throwable exception = assertThrows(RuntimeException.class,
                () -> fileWriterCsv.write(FILE_NAME, emptyReport));
        assertEquals("Report is empty", exception.getMessage());
    }

    @Test
    void write_fileNameIsEmpty_notOk() {
        String emptyFileName = "";
        Throwable exception = assertThrows(RuntimeException.class,
                () -> fileWriterCsv.write(emptyFileName, report));
        assertEquals("File name is empty", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "src/test/resources/writer/reportCsv.xml",
            "src/test/resources/writer/reportCsv.txt",
            "src/test/resources/writer/reportCsv.doc",
            "src/test/resources/writer/reportCsv.jpg",
            "src/test/resources/writer/reportCsv.java",
            "src/test/resources/writer/reportCsv.pdf"
    })
    void write_notCsvFileExtension_notOk(String path) {
        Throwable exception = assertThrows(RuntimeException.class,
                () -> fileWriterCsv.write(path, report));
        assertEquals("""
                The file extension is different from .csv: %s"""
                .formatted(path), exception.getMessage());
    }

    @Test
    void write_nullValueInReport_notOk() {
        List<String> listWithNull1 = Arrays.asList(null, null, null, null);
        List<String> listWithNull2 = Arrays.asList("orange,405", "lemon,100", "peach,623", null);
        Throwable exception1 = assertThrows(RuntimeException.class,
                () -> fileWriterCsv.write(FILE_NAME, listWithNull1));
        assertEquals("Report contains null value", exception1.getMessage());
        Throwable exception2 = assertThrows(RuntimeException.class,
                () -> fileWriterCsv.write(FILE_NAME, listWithNull2));
        assertEquals("Report contains null value", exception2.getMessage());
    }

    @Test
    void write_correctParameters_ok() {
        List<String> linesFromFile;
        fileWriterCsv.write(FILE_NAME, report);
        try {
            Path path = Path.of(FILE_NAME);
            linesFromFile = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file");
        }
        assertArrayEquals(report.toArray(), linesFromFile.toArray());
    }

    @Test
    void write_fileAlreadyExist_ok() {
        List<String> previousReport = List.of(
                "apple,45",
                "orange,234",
                "lemon,68",
                "peach,233",
                "pear,0",
                "pineapple,1000");
        fileWriterCsv.write(FILE_NAME,previousReport);
        List<String> linesFromPreviousFile;
        try {
            Path path = Path.of(FILE_NAME);
            linesFromPreviousFile = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file");
        }
        fileWriterCsv.write(FILE_NAME, report);
        List<String> linesFromFile;
        try {
            Path path = Path.of(FILE_NAME);
            linesFromFile = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file");
        }
        assertArrayEquals(previousReport.toArray(), linesFromPreviousFile.toArray());
        assertArrayEquals(report.toArray(), linesFromFile.toArray());
    }
}
