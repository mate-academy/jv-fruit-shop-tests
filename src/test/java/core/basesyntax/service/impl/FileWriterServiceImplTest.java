package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.AfterClass;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final FileWriterService fileWriterService = new FileWriterServiceImpl();
    private static final String VALID_PATH = "src/test/resources/newFile.csv";

    @AfterClass
    public static void deleteFile() {
        try {
            Files.writeString(Path.of(VALID_PATH), "");
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to file ", e);
        }
    }
        @Test
    public void writeToFile_validPathAndString_Ok() {
        String expected = "Hope you still don't want to reject it!";
        fileWriterService.writeToFile(expected, VALID_PATH);
        String actual;
        try {
            actual = Files.readString(Path.of(VALID_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from path: " + VALID_PATH, e);
        }
        assertEquals("Data should be written to file", expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_nullPath_NotOk() {
        fileWriterService.writeToFile("null", null);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_invalidPath_NotOk() {
        fileWriterService.writeToFile("null", VALID_PATH + "/notExist");
    }
}
