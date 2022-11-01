package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.FileWriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import org.junit.Test;

public class FileWriterServiceTest {
    private static final String VALID_DATA = "line1" + System.lineSeparator() + "line2";
    private static final String VALID_PATH = "test_output.txt";
    private static final String INVALID_PATH = "T:////invalidPath?><..";
    private final FileWriterService fileWriterService = new FileWriterServiceImpl();

    @Test
    public void writeToFile_validPathValidContent_writtenToFile_ok() {
        fileWriterService.writeToFile(VALID_PATH, VALID_DATA);
        try {
            String actual = Files.readString(Path.of(VALID_PATH));
            assertEquals("Test failed! Expected content of written file: "
                    + VALID_PATH + " is: " + VALID_DATA
                    + ", but was: " + actual,
                    VALID_DATA, actual);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void writeToFile_validPath_emptyData_emptyFile_ok() {
        fileWriterService.writeToFile(VALID_PATH, "");
        try {
            String actual = Files.readString(Path.of(VALID_PATH));
            assertEquals("Test failed! Expected empty file on path: "
                            + VALID_PATH
                            + ", but was: " + actual,
                    "", actual);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_invalidPath_notOk() {
        fileWriterService.writeToFile(INVALID_PATH, VALID_DATA);
    }

}
