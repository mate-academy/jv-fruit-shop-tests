package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.DataReader;
import core.basesyntax.service.impl.exception.InvalidDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataReaderImplTest {
    private DataReader dataReader;

    @BeforeEach
    void setUp() {
        dataReader = new DataReaderImpl();
    }

    @Test
    void readDataFromFile_nullFile_notOk() {
        String nullString = null;
        assertThrows(NullPointerException.class, () -> dataReader.readDataFromFile(nullString),
                "NullPointerException expected to be thrown");
    }

    @Test
    void readDataFromFile_invalidFilePath_notOk() {
        String invalidFilePath = "";
        assertThrows(InvalidDataException.class, () -> dataReader.readDataFromFile(invalidFilePath),
                "InvalidDataException expected to be thrown");
    }

    @Test
    void readDataFromFile_validFilePath_Ok() {
        String validFilePath = "src/test/resources/testInputFile.csv";
        String dataFromValidPath = "test";
        assertEquals(dataReader.readDataFromFile(validFilePath), dataFromValidPath);
    }
}
