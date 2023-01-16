package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReadService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReadServiceImplTest {
    private static final Path VALID_PATH = Path.of("src/test/resources/input.csv");
    private static FileReadService fileReadService;

    @BeforeClass
    public static void setUp() {
        fileReadService = new FileReadServiceImpl();
    }

    @Test
    public void readFromFile_validFilePath_ok() throws IOException {
        String expected = Files.readString(VALID_PATH);
        String actual = fileReadService.readFromFile(VALID_PATH);
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_nonExistentFilePath_notOk() {
        fileReadService.readFromFile(Path.of("src/main/resources/non_existent.csv"));
    }

    @Test (expected = NullPointerException.class)
    public void readFromFile_nullFilePath_notOk() {
        fileReadService.readFromFile(null);
    }
}