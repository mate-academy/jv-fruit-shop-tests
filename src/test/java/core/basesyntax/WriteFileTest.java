package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriteFileService;
import core.basesyntax.service.impl.WriteFileServiceImpl;
import core.basesyntax.strorage.FruitStorage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriteFileTest {
    private static final String VALID_PATH_FILE = "src/test/resources/testResult.csv";
    private static final String NOT_VALID_PATH = "";
    private static final String DATA = "type,quantity" + System.lineSeparator() + "banana,25";

    private static WriteFileService writeFileService;

    @BeforeClass
    public static void setUp() {
        writeFileService = new WriteFileServiceImpl();
    }

    @Before
    public void clearStorage() {
        FruitStorage.fruits.clear();
    }

    @Test
    public void writeToFile_validPath_ok() throws IOException {
        Path path = Path.of(VALID_PATH_FILE);
        writeFileService.writeToFile(path, DATA);
        assertEquals("Not valid path " + VALID_PATH_FILE, DATA, Files.readString(path));
    }

    @Test(expected = NullPointerException.class)
    public void writeToFile_nullInput_notOk() {
        writeFileService.writeToFile(null,null);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_invalidPathToFile() {
        writeFileService.writeToFile(Path.of(NOT_VALID_PATH), DATA);
    }

}
