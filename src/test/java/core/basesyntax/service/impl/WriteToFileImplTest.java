package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.WriteToFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WriteToFileImplTest {
    private static final String REPORT_FILE = "src/test/java/resources/report.txt";
    private WriteToFile writer;
    private List<String> report;

    @Before
    public void setUp() {
        writer = new WriteToFileImpl();
        report = new ArrayList<>();
        report.add("banana,152" + System.lineSeparator());
        report.add("apple,50" + System.lineSeparator());
    }

    @Test
    public void writeToFile_filePathIsNull_notOk() {
        try {
            writer.writeToFile(null, report);
            Assert.fail("Expected RunTimeException");
        } catch (RuntimeException e) {
            assertEquals("FilePath must be matched", e.getMessage());
        }
    }

    @Test
    public void writeToFile_reportIsNull_notOk() {
        try {
            writer.writeToFile(REPORT_FILE, null);
            Assert.fail("Expected RunTimeException");
        } catch (RuntimeException e) {
            assertEquals("Report must be matched", e.getMessage());
        }
    }

    @Test
    public void writeToFile_reportIsEmpty_ok() {
        List<String> emptyReport = new ArrayList<>();
        assertFalse(writer.writeToFile(REPORT_FILE, emptyReport));
    }

    @Test
    public void writeToFile_wrongFilePath_notOk() {
        String wrongFilePath = "";
        try {
            writer.writeToFile(wrongFilePath, report);
            Assert.fail();
        } catch (RuntimeException e) {
            assertEquals("Can't find such file", e.getMessage());
        }
    }

    @Test
    public void writeToFile_rightWriting_ok() {
        assertTrue(writer.writeToFile(REPORT_FILE, report));
        List<String> reportFromFile;
        try {
            reportFromFile = Files.readAllLines(Paths.get(REPORT_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(report, reportFromFile.stream()
                .map(s -> s + System.lineSeparator())
                .collect(Collectors.toList()));
    }
}
