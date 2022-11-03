package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.WriteToFile;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class WriteToFileImplTest {
    private static final String REPORT_FILE = "src/main/resources/report.txt";
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
        } catch (RuntimeException e) {
            assertEquals("FilePath must be matched", e.getMessage());
        }
    }

    @Test
    public void writeToFile_reportIsNull_notOk() {
        try {
            writer.writeToFile(REPORT_FILE, null);
        } catch (RuntimeException e) {
            assertEquals("Report must be matched", e.getMessage());
        }
    }

    @Test
    public void writeToFile_reportIsEmpty_notOk() {
        List<String> emptyReport = new ArrayList<>();
        assertFalse(writer.writeToFile(REPORT_FILE, emptyReport));
    }

    @Test
    public void writeToFile_wrongFilePath_notOk() {
        String wrongFilePath = "";
        try {
            writer.writeToFile(wrongFilePath, report);
        } catch (RuntimeException e) {
            assertEquals("Can't find such file", e.getMessage());
        }
    }

    @Test
    public void writeToFile_rightWrighting_ok() {
        assertTrue(writer.writeToFile(REPORT_FILE, report));
    }
}
