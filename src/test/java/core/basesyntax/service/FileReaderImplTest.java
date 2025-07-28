package core.basesyntax.service;

import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class FileReaderImplTest {
    private static final String FILE_TO = "src/test/resources/TestDataBase.csv";
    private Reader reader = new FruitReaderImpl();

    @Test
    void fileReader_ReadFile_Ok() {
        reader.read(FILE_TO);
    }

    @Test
    void fileReader_ReadEmptyFile_NotOk() {
        assertThrows(RuntimeException.class, () ->
                reader.read(""));
    }
}

