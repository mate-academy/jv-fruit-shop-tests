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
    public static final String FILE = "src/test/java/resources/input_FileWriter.csv";
    public static final String NOT_EXISTING_PATH = "";
    public static final String DEFAULT_STRING = "fruit,quantity\n" + "banana,152";
    private FileWriterService fileWriter;

    @Before
    public void setUp() {
        fileWriter = new FileWriterServiceImpl();
    }

    @Test
    public void writeToFile_ExistingPath_Ok() throws IOException {
        fileWriter.writeToFile(FILE, DEFAULT_STRING);
        List<String> expected = List.of("fruit,quantity", "banana,152");
        List<String> actual = Files.readAllLines(Path.of(FILE));
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_NotExistingPath_NotOk() {
        fileWriter.writeToFile(NOT_EXISTING_PATH, DEFAULT_STRING);
    }
}
