package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void setUp() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void readFromFile_validFilePath_ok() throws IOException {
        Path validPath = Path.of("src/test/resources/input.csv");
        String expected = Files.readString(validPath);
        String actual = fileReaderService.readFromFile(validPath);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_nonExistentFilePath_notOk() {
        fileReaderService.readFromFile(Path.of("src/test/resources/non_existent.csv"));
    }

    @Test (expected = NullPointerException.class)
    public void readFromFile_nullFilePath_notOk() {
        fileReaderService.readFromFile(null);
    }
}
