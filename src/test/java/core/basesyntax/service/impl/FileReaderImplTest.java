package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import org.junit.jupiter.api.Test;

class FileReaderImplTest {
    private static final String VALID_INPUT_FILE_PATH = "src/test/resources/input.csv";
    private static final String INVALID_INPUT_FILE_PATH = "src/test/resources/invalidinput.csv";
    private static final FileReader fileReader = new FileReaderImpl();

    @Test
    void readFromFile_fileNotExists_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileReader.readFromFile(INVALID_INPUT_FILE_PATH));
    }

    @Test
    void readFromFile_readData_isOk() {
        String expectedData = "b,banana,20;b,apple,100;s,banana,100;p,banana,10;r,apple,10;";
        assertEquals(expectedData,fileReader.readFromFile(VALID_INPUT_FILE_PATH));
    }
}
