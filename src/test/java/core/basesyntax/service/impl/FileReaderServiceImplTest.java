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
    private static final String VALID_INPUT_PATH = "src/test/resources/input_test_file.csv";
    private static final String INVALID_INPUT_PATH = "";
    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    public void readAllLines_isOk() throws IOException {
        List<String> expected = Files.readAllLines(Path.of(VALID_INPUT_PATH));
        List<String> actual = fileReaderService.readAllLines(VALID_INPUT_PATH);
        assertEquals(expected, actual);

    }

    @Test (expected = RuntimeException.class)
    public void readAllLines_nonExistingFilePath_isNotOk() {
        fileReaderService.readAllLines(INVALID_INPUT_PATH);
    }
}
