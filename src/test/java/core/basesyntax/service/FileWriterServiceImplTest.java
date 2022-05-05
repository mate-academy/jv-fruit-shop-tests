package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.FileWriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String VALID_PATH = "src/test/resources/report.csv";
    private static final String INVALID_PATH = "C:Users/resources/invalidReport.csv";
    private static String report;
    private static FileWriterService fileWriter;
    private static List<String> expected;

    @BeforeClass
    public static void beforeClass() {
        report = "fruit,quantity" + System.lineSeparator()
                + "banana,10"
                + System.lineSeparator()
                + "apple,20"
                + System.lineSeparator()
                + "mango,30"
                + System.lineSeparator();
        expected = new ArrayList<>();
        expected.add("fruit,quantity");
        expected.add("banana,10");
        expected.add("apple,20");
        expected.add("mango,30");
        fileWriter = new FileWriterServiceImpl();
    }

    @Test
    public void writeFile_isOk() {
        fileWriter.writeToFile(VALID_PATH, report);
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(VALID_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't find data", e);
        }
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeFile_notOk() {
        fileWriter.writeToFile(INVALID_PATH, report);
    }

    @Test (expected = NullPointerException.class)
    public void writeToFile_nullFilePath_NotOk() {
        fileWriter.writeToFile(null, report);
    }

    @Test (expected = NullPointerException.class)
    public void write_nullReport_NotOk() {
        fileWriter.writeToFile(VALID_PATH, null);
    }
}
