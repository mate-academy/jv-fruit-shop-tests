package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static final String VALID_FILE_NAME = "src/test/resources/input.csv";

    @Test
    public void fileReaderServiceImpl_readFromValidFile_Ok() {
        assertEquals(validReadFromFile(VALID_FILE_NAME),
                new FileReaderServiceImpl().readFromFile(VALID_FILE_NAME));
    }

    @Test(expected = RuntimeException.class)
    public void fileReaderServiceImpl_readInvalidFileName_NotOk() {
        String testString = new FileReaderServiceImpl().readFromFile("qwerty");
    }

    private String validReadFromFile(String fileName) {
        try {
            return Files.readString(Path.of(fileName)) + System.lineSeparator();
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly read data from file " + fileName, e);
        }
    }
}
