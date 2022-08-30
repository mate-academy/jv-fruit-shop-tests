package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String OUTPUT_TEST_PATH = "src/test/resources/outputTest.csv";
    private static final String INPUT_TEST_PATH = "src/test/resources/inputTest.csv";
    private static String string;
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new FileWriterServiceImpl();
        string = ("type,fruit,amount\n"
                + "b,apple,90\n"
                + "b,banana,30\n"
                + "s,banana,60\n"
                + "s,banana,40\n"
                + "p,banana,25\n"
                + "r,apple,10\n"
                + "p,apple,20\n"
                + "p,banana,15");
    }

    @Test
    public void writeToFile_existingPathFile_Ok() {
        fileWriterService.writeToFile(OUTPUT_TEST_PATH, string);
        List<String> expected = readFromTestFile(INPUT_TEST_PATH);
        List<String> actual = readFromTestFile(OUTPUT_TEST_PATH);
        assertEquals(expected, actual);
    }

    @Test
    public void writeToFile_emptyData_Ok() {
        fileWriterService.writeToFile(OUTPUT_TEST_PATH, "");
        List<String> expected = List.of();
        List<String> actual = readFromTestFile(OUTPUT_TEST_PATH);
        assertEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void writeToFile_nullBuilder_NotOk() {
        fileWriterService.writeToFile(OUTPUT_TEST_PATH, null);
    }

    @Test (expected = NullPointerException.class)
    public void writeToFile_nullPath_NotOk() {
        fileWriterService.writeToFile(null, string);
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_emptyPath_NotOk() {
        fileWriterService.writeToFile("", string);
    }

    private List<String> readFromTestFile(String fromFilePath) {
        List<String> data;
        try {
            data = Files.readAllLines(Path.of(fromFilePath));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file " + fromFilePath, e);
        }
        return data;
    }
}
