package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String VALID_PATH = "src/main/resources/input.csv";
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void setUp() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void readFromFile_validPath_ok() throws IOException {
        List<String> expected = Files.readAllLines(Path.of(VALID_PATH));
        List<String> actual = fileReaderService.readFromFile(VALID_PATH);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_nullPath_notOk() {
        fileReaderService.readFromFile(null);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_nonExistentPath_notOk() {
        fileReaderService.readFromFile("non_existent_path");
    }
}
