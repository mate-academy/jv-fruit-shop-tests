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
    private static final String SECOND_REPORT =
            "mellon,10" + System.lineSeparator()
                    + "peach,20" + System.lineSeparator()
                    + "tomato,30" + System.lineSeparator()
                    + "orange,40" + System.lineSeparator();
    private static final String THIRD_REPORT =
            "line1,10" + System.lineSeparator()
                    + "line2,20" + System.lineSeparator()
                    + "line3,30" + System.lineSeparator()
                    + "line4,40" + System.lineSeparator()
                    + "line5,50" + System.lineSeparator()
                    + "line6,60" + System.lineSeparator()
                    + "line7,70" + System.lineSeparator();
    private static final String FIRST_REPORT_PATH =
            "src/test/resources/output/report1.csv";
    private static final String SECOND_REPORT_PATH =
            "src/test/resources/output/report2.csv";
    private static final String THIRD_REPORT_PATH =
            "src/test/resources/output/report3.csv";
    private static final String EMPTY_REPORT_PATH =
            "src/test/resources/output/empty.csv";

    private FileWriter fileWriter;

    @Before
    public void setup() {
        fileWriter = new FileWriterImpl();
    }

    @After
    public void deleteFiles() {
        File file = new File(FIRST_REPORT_PATH);
        file.delete();
        file = new File(SECOND_REPORT_PATH);
        file.delete();
        file = new File(THIRD_REPORT_PATH);
        file.delete();
    }

    @Test
    public void writeToFile_ok() {
        fileWriter.writeReport(FIRST_REPORT_PATH, FIRST_REPORT);
        String actual = readFile(FIRST_REPORT_PATH);
        File file = new File(FIRST_REPORT_PATH);
        assertTrue(file.exists());
        assertEquals(FIRST_REPORT, actual);
        fileWriter.writeReport(SECOND_REPORT_PATH, SECOND_REPORT);
        actual = readFile(SECOND_REPORT_PATH);
        file = new File(SECOND_REPORT_PATH);
        assertTrue(file.exists());
        assertEquals(SECOND_REPORT, actual);
        fileWriter.writeReport(THIRD_REPORT_PATH, THIRD_REPORT);
        actual = readFile(THIRD_REPORT_PATH);
        file = new File(THIRD_REPORT_PATH);
        assertTrue(file.exists());
        assertEquals(THIRD_REPORT, actual);
    }

    @Test
    public void writeEmptyReport_ok() {
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
