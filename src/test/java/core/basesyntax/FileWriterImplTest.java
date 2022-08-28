package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.FileWriter;
import core.basesyntax.service.impl.FileWriterImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String FIRST_REPORT =
            "apple,100" + System.lineSeparator()
            + "banana,200" + System.lineSeparator()
            + "mango,300" + System.lineSeparator()
            + "kiwi,400" + System.lineSeparator();
    private static final String FIRST_REPORT_PATH =
            "src/test/resources/output/report1.csv";
    private static final String EMPTY_REPORT_PATH =
            "src/test/resources/output/empty.csv";
    private FileWriter fileWriter;
    private File file;

    @Before
    public void setup() {
        fileWriter = new FileWriterImpl();
    }

    @After
    public void deleteFile() {
        file.delete();
    }

    @Test
    public void writeReport_ExistingInput_Ok() {
        fileWriter.writeReport(FIRST_REPORT_PATH, FIRST_REPORT);
        String actual = readFile(FIRST_REPORT_PATH);
        file = new File(FIRST_REPORT_PATH);
        assertTrue(file.exists());
        assertEquals(FIRST_REPORT, actual);
    }

    @Test
    public void writeReport_WithEmptyInputs_Ok() {
        file = new File(EMPTY_REPORT_PATH);
        fileWriter.writeReport(EMPTY_REPORT_PATH, "");
        String actual = readFile(EMPTY_REPORT_PATH);
        assertTrue(actual.isEmpty());
    }

    private static String readFile(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file", e);
        }
    }
}
