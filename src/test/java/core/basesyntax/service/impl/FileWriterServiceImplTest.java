package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String FILE_NAME_TO = "src/test/resources/report.csv";
    private static FileWriterService fileWriterService;
    private static String data;

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new FileWriterServiceImpl();
        data = "fruit,quantity"
                + System.lineSeparator() + "banana,152"
                + System.lineSeparator() + "apple,90";
    }

    @Test
    public void writeToFile_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("fruit,quantity");
        expected.add("banana,152");
        expected.add("apple,90");
        List<String> actual;
        fileWriterService.writeToFile(data, FILE_NAME_TO);

        try {
            actual = Files.readAllLines(Path.of(FILE_NAME_TO));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file");
        }

        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_incorrectPath_notOk() {
        fileWriterService.writeToFile(data, "");
    }
}
