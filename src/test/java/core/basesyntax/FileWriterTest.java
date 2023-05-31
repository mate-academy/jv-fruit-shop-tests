package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.impl.WriteFileServiceImpl;
import core.basesyntax.strorage.FruitStorage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterTest {
    private static final String VALID_PATH_FILE = "src/test/resources/testResult.csv";
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void setUp() {
        fileWriterService = new WriteFileServiceImpl();
    }

    @Before
    public void clearStorage() {
        FruitStorage.fruits.clear();
    }

    @Test
    public void writeToFile_validPath_ok() throws IOException {
        String data = "type,quantity"
                + System.lineSeparator()
                + "banana,25";
        Path path = Path.of(VALID_PATH_FILE);
        fileWriterService.writeToFile(path, data);
        assertEquals("Not valid path " + VALID_PATH_FILE, data, Files.readString(path));
    }

    @Test(expected = NullPointerException.class)
    public void writeToFile_nullInput_notOk() {
        fileWriterService.writeToFile(null,null);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_invalidPathToFile() {
        String data = "type,quantity"
                + System.lineSeparator()
                + "banana,25";
        fileWriterService.writeToFile(Path.of(""), data);
    }
}
