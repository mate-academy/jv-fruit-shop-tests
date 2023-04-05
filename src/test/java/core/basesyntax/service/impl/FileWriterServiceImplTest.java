package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FileWriterServiceImplTest {
    public static final String OUTPUT_DATA_VALID_PATH = "src/test/resources/output.csv";
    public static final String OUTPUT_DATA_INVALID_PATH = "scr/test/resources/";
    public static final String OUTPUT_DATA_STRING = "fruit,quantity\n"
            + "banana,332\n"
            + "apple,90";
    private FileWriterService fileWriterService;

    @Before
    public void setUp() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void writeToFile_validPath_Ok() {
        fileWriterService.writeToFile(OUTPUT_DATA_STRING, OUTPUT_DATA_VALID_PATH);
        List<String> actual;
        List<String> expected = List.of("fruit,quantity", "banana,332", "apple,90");
        try {
            actual = Files.readAllLines(Path.of(OUTPUT_DATA_VALID_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file " + OUTPUT_DATA_VALID_PATH, e);
        }
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_invalidPath_NotOk() {
        fileWriterService.writeToFile(OUTPUT_DATA_STRING, OUTPUT_DATA_INVALID_PATH);
    }
}
