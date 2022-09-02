package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String PATH_OF_OUTPUT_FILE = "src/test/resources/output.csv";
    private static WriterService writerService;

    @Before
    public void setUp() throws Exception {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void writeToFile_isCheck_ok() {
        String report =
                "fruit, quantity"
                        + System.lineSeparator()
                        + "banana,152"
                        + System.lineSeparator()
                        + "apple,90";
        List<String> expected = List.of("fruit, quantity", "banana,152", "apple,90");
        writerService.writeToFile(report, PATH_OF_OUTPUT_FILE);
        List<String> actual = readFromFile(PATH_OF_OUTPUT_FILE);
        Assert.assertEquals(expected, actual);
    }

    public List<String> readFromFile(String fileName) {
        try {
            return Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }
    }
}
