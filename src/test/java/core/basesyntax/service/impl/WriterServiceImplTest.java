package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String REPORT_FILE_CSV = "src/test/java/resources/report.csv";

    private static WriterService writerService;

    @BeforeClass
    public static void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void writeDataToFile_ok() {
        String data = "Data " + System.lineSeparator()
                + "Data 2 " + System.lineSeparator()
                + "HalfLive 3" + System.lineSeparator()
                + "Data 4";
        writerService.writeToFile(REPORT_FILE_CSV, data);
        List<String> expected = new ArrayList<>();
        expected.add("Data ");
        expected.add("Data 2 ");
        expected.add("HalfLive 3");
        expected.add("Data 4");
        List<String> actual = readeFromFile(REPORT_FILE_CSV);
        assertEquals(expected, actual);
    }

    @Test
    public void writeEmptyStringToFile_ok() {
        writerService.writeToFile(REPORT_FILE_CSV, "");
        List<String> actual = readeFromFile(REPORT_FILE_CSV);
        List<String> expected = new ArrayList<>();
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void incorrectFileName_notOk() {
        writerService.writeToFile("", "");
    }

    @Test (expected = RuntimeException.class)
    public void nullFileName_notOk() {
        writerService.writeToFile(null, "");
    }

    private List<String> readeFromFile(String fileName) {
        try {
            return Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file: " + fileName, e);
        }
    }
}
