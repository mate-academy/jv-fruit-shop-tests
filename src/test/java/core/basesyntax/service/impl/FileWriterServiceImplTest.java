package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String VALID_FILE_NAME = "src/test/resources/output.csv";
    private static final String DATA = "type,fruit,quantity" + System.lineSeparator()
            + "b,apple,100" + System.lineSeparator()
            + "s,banana,100" + System.lineSeparator()
            + "p,banana,13";

    @Test
    public void fileWriterServiceImpl_writeToValidFile_Ok() {
        new FileWriterServiceImpl().writeToFile(DATA, VALID_FILE_NAME);
        assertEquals(DATA,
                readFromFile(VALID_FILE_NAME));
    }

    @Test(expected = RuntimeException.class)
    public void fileWriterServiceImpl_readInvalidFileName_NotOk() {
        new FileWriterServiceImpl().writeToFile(null, "123321");
    }

    private String readFromFile(String fileName) {
        try {
            return Files.readString(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly read data from file " + fileName, e);
        }
    }
}
