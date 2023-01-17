package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReadService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReadServiceImplTest {
    private static FileReadService fileReadService;

    @BeforeClass
    public static void setUp() {
        fileReadService = new FileReadServiceImpl();
    }

    @Test
    public void readFromFile_validFilePath_ok() throws IOException {
        Path validPath = Path.of("src/test/resources/input.csv");
        String expected = Files.readString(validPath);
        String actual = fileReadService.readFromFile(validPath);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_nonExistentFilePath_notOk() {
        fileReadService.readFromFile(Path.of("src/test/resources/non_existent.csv"));
    }

    @Test (expected = NullPointerException.class)
    public void readFromFile_nullFilePath_notOk() {
        fileReadService.readFromFile(null);
    }
}
