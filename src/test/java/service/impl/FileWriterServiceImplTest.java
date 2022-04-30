package service.impl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static String report;
    private static FileWriterServiceImpl fileWriter;
    private static List<String> expected;
    private static List<String> actual;
    private static String filePath;

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
        filePath = "src/test/resources/report.csv";
        fileWriter.writeFile(filePath, report);
        try {
            actual = Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Can't find data in file" + filePath, e);
        }
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeFile_withInvalidPath_notOk() {
        filePath = "C:Users/QQ";
        fileWriter.writeFile(filePath, report);
    }
}
