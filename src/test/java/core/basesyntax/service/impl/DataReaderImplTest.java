package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.DataReader;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataReaderImplTest {
    private static final String CORRECT_FILE_PATH = "src/test/resources/input.csv";
    private static final String INCORRECT_FILE_PATH = "src/test/resources/IncorrectInput.csv";
    private final DataReader dataReader = new DataReaderImpl();

    @Test
    void read_correctFile_ok() {
        List<String> actual = dataReader.read(CORRECT_FILE_PATH);
        assertNotNull(actual);
    }

    @Test
    void read_incorrectFile_notOk() {
        assertThrows(RuntimeException.class, () -> dataReader.read(INCORRECT_FILE_PATH));
    }
}
