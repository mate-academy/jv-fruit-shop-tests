package core.basesyntax.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String FILE_OUTPUT_PATH = "src/test/resources/output.csv";
    private static final String FILE_INPUT_PATH = "src/test/resources/input.csv";
    private static StringBuilder stringBuilder;
    private static FileWriterService fileWriterService;

    @Before
    public void setUp() {
        stringBuilder = new StringBuilder();
        stringBuilder.append("type,fruit,quantity")
                .append(System.lineSeparator()).append("b,banana,20")
                .append(System.lineSeparator());
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void write() {
        fileWriterService.write(stringBuilder.toString(), FILE_OUTPUT_PATH);
        List<String> expected = readFromFile(FILE_INPUT_PATH);
        List<String> actual = readFromFile(FILE_OUTPUT_PATH);
        Assert.assertEquals(expected, actual);
    }

    private List<String> readFromFile(String filePath) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file " + filePath, e);
        }
        return lines;
    }
}
