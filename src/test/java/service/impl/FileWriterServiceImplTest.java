package service.impl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import service.FileWriterService;

public class FileWriterServiceImplTest {
    private static final String VALID_PATH = "src/test/resources/report.csv";
    private static final String INVALID_PATH = "C:Users/QQ";
    private static String report;
    private static FileWriterService fileWriter;
    private static List<String> expected;

    @BeforeClass
    public static void beforeClass() {
        report = "fruit,quantity" + System.lineSeparator()
                + "banana,200"
                + System.lineSeparator()
                + "apple,91"
                + System.lineSeparator();
        expected = new ArrayList<>();
        expected.add("fruit,quantity");
        expected.add("banana,200");
        expected.add("apple,91");
        fileWriter = new FileWriterServiceImpl();
    }

    @Test
    public void writeFile_withValidPath_isOk() {
        fileWriter.writeFile(VALID_PATH, report);
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(VALID_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't find data in file" + VALID_PATH, e);
        }
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeFile_withInvalidPath_notOk() {
        fileWriter.writeFile(INVALID_PATH, report);
    }
}
