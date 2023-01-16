package core.basesyntax.db.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String VALID_PATH = "src/test/resources/report.csv";
    private static final String INVALID_PATH = "invalid_path";
    private static final String TEST_SENTENCE = "test sentence";
    private static FileWriterService fileWriterService;

    @BeforeClass
    public static void setUp() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void writeToFile_validPath_ok() {
        fileWriterService.writeToFile(TEST_SENTENCE, VALID_PATH);
        String actual;
        try {
            actual = Files.readString(Path.of(VALID_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(TEST_SENTENCE, actual);
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_InvalidPath_notOk() {
        fileWriterService.writeToFile(TEST_SENTENCE, INVALID_PATH);
    }

    @Test (expected = RuntimeException.class)
    public void writeToFile_pathIsNull() {
        fileWriterService.writeToFile(TEST_SENTENCE, null);
    }

    @Test (expected = NullPointerException.class)
    public void writeToFile_dataIsNull() {
        fileWriterService.writeToFile(null, VALID_PATH);
    }
}
