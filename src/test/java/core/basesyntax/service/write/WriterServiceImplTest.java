package core.basesyntax.service.write;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String INPUT_PATH = "src/test/resources/validData.csv";
    private static final String OUTPUT_PATH = "src/test/resources/emptyFile.csv";
    private static final String SEPARATOR = System.lineSeparator();
    private static String content;
    private static WriterService writerService;

    @BeforeClass
    public static void beforeClass() {
        writerService = new WriterServiceImpl();
        content = "type,fruit,quantity"
                + SEPARATOR + "b,banana,20"
                + SEPARATOR + "b,apple,100"
                + SEPARATOR + "s,banana,100";
    }

    @Test
    public void write_validPathFile_OK() {
        writerService.write(OUTPUT_PATH, content);
        List<String> expected = readFromTestFile(INPUT_PATH);
        List<String> actual = readFromTestFile(OUTPUT_PATH);
        Assert.assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void write_emptyPath_NotOk() {
        writerService.write("", content);
    }

    @Test
    public void write_emptyData_Ok() {
        writerService.write(OUTPUT_PATH, "");
        List<String> expected = new ArrayList<>();
        List<String> actual = readFromTestFile(OUTPUT_PATH);
        Assert.assertEquals(expected, actual);
    }

    private List<String> readFromTestFile(String fromFilePath) {
        try {
            return Files.readAllLines(Path.of(fromFilePath));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file " + fromFilePath, e);
        }
    }
}
