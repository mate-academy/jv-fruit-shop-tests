package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static String report;
    private static FileWriter fileWriter;
    private static List<String> expected;
    private static List<String> actual;
    private String filePath;

    @BeforeClass
    public static void beforeClass() {
        report = "fruit,quantity" + System.lineSeparator()
                + "banana,200"
                + System.lineSeparator()
                + "apple,91"
                + System.lineSeparator();
        actual = new ArrayList<>();
        expected = new ArrayList<>();
        expected.add("fruit,quantity");
        expected.add("banana,200");
        expected.add("apple,91");
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void writeFile_WithValidPath_Ok() {
        filePath = "src/test/resources/report.csv";
        fileWriter.writeFile(filePath, report);
        try {
            actual = Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Cant find data in file" + filePath);
        }
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeFile_PathToFileIsNotCorrect_NotOk() {
        filePath = "C//helloWorld";
        fileWriter.writeFile(filePath, report);
    }
}
