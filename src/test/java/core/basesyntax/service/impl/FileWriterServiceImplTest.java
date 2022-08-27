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
    private static final StringBuilder builder = new StringBuilder();
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new FileWriterServiceImpl();
        builder.append("type,fruit,amount")
                .append(System.lineSeparator()).append("b,apple,90")
                .append(System.lineSeparator()).append("b,banana,30")
                .append(System.lineSeparator()).append("s,banana,60")
                .append(System.lineSeparator()).append("s,banana,40")
                .append(System.lineSeparator()).append("p,banana,25")
                .append(System.lineSeparator()).append("r,apple,10")
                .append(System.lineSeparator()).append("p,apple,20")
                .append(System.lineSeparator()).append("p,banana,15");
    }

    @Test
    public void writeToFile_existingPathFile_Ok() {
        fileWriterService.writeToFile(OUTPUT_TEST_PATH, builder.toString());
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
        fileWriterService.writeToFile(null, builder.toString());
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_emptyPath_NotOk() {
        fileWriterService.writeToFile("", builder.toString());
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
