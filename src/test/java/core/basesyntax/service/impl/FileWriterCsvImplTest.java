package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    private static final Writer FILE_WRITER_CSV = new FileWriterCsvImpl();
    private static List<String> report;
    private static String fileName;

    @BeforeAll
    static void beforeAll() {
        fileName = "src/test/resources/writer/reportCsv.csv";
        report = new ArrayList<>();
        report.add("line1");
        report.add("line2");
        report.add("line3");
        report.add("line4");
        report.add("line5");
        report.add("line6");
    }

    @Test
    void write_nullParameters_notOk() {
        String nullName = null;
        List<String> nullList = null;
        Throwable exception1 = assertThrows(IllegalArgumentException.class,
                () -> FILE_WRITER_CSV.write(nullName, nullList));
        assertEquals("Parameters can't be null, but:"
                + "\nfileName = " + nullName
                + "\nreport = " + nullList, exception1.getMessage());
        Throwable exception2 = assertThrows(IllegalArgumentException.class,
                () -> FILE_WRITER_CSV.write(fileName, nullList));
        assertEquals("Parameters can't be null, but:"
                + "\nfileName = " + fileName
                + "\nreport = " + nullList, exception2.getMessage());
        Throwable exception3 = assertThrows(IllegalArgumentException.class,
                () -> FILE_WRITER_CSV.write(nullName, report));
        assertEquals("Parameters can't be null, but:"
                + "\nfileName = " + nullName
                + "\nreport = " + report, exception3.getMessage());
    }

    @Test
    void write_reportIsEmpty_notOk() {
        List<String> emptyReport = new ArrayList<>();
        Throwable exception = assertThrows(RuntimeException.class,
                () -> FILE_WRITER_CSV.write(fileName, emptyReport));
        assertEquals("Report is empty", exception.getMessage());
    }

    @Test
    void write_fileNameIsEmpty_notOk() {
        String emptyFileName = "";
        Throwable exception = assertThrows(RuntimeException.class,
                () -> FILE_WRITER_CSV.write(emptyFileName, report));
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
                () -> FILE_WRITER_CSV.write(path, report));
        assertEquals("The file extension is different from .csv: "
                 + path, exception.getMessage());
    }

    @Test
    void write_nullValueInReport_notOk() {
        List<String> listWithNull1 = new ArrayList<>();
        listWithNull1.add(null);
        listWithNull1.add(null);
        listWithNull1.add(null);
        listWithNull1.add(null);
        List<String> listWithNull2 = new ArrayList<>();
        listWithNull2.add("line");
        listWithNull2.add("line");
        listWithNull2.add("line");
        listWithNull2.add(null);
        Throwable exception1 = assertThrows(RuntimeException.class,
                () -> FILE_WRITER_CSV.write(fileName, listWithNull1));
        assertEquals("Report contains null value", exception1.getMessage());
        Throwable exception2 = assertThrows(RuntimeException.class,
                () -> FILE_WRITER_CSV.write(fileName, listWithNull2));
        assertEquals("Report contains null value", exception2.getMessage());
    }

    @Test
    void write_correctParameters_ok() {
        List<String> linesFromFile;
        FILE_WRITER_CSV.write(fileName, report);
        try {
            Path path = Path.of(fileName);
            linesFromFile = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file");
        }
        boolean isListEquals = Arrays.equals(report.toArray(), linesFromFile.toArray());
        assertTrue(isListEquals);
    }

    @Test
    void write_fileAlreadyExist_ok() {
        List<String> linesFromFile;
        List<String> recentReport = new ArrayList<>();
        recentReport.add("previous line 1");
        recentReport.add("previous line 2");
        recentReport.add("previous line 3");
        recentReport.add("previous line 4");
        FILE_WRITER_CSV.write(fileName,recentReport);
        FILE_WRITER_CSV.write(fileName, report);
        try {
            Path path = Path.of(fileName);
            linesFromFile = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file");
        }
        boolean isListEquals = Arrays.equals(report.toArray(), linesFromFile.toArray());
        assertTrue(isListEquals);
    }
}
