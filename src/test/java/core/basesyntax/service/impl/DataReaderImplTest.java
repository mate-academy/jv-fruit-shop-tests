package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.DataReader;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataReaderImplTest {
    private static final List<String> EXPECTED_DATA = List.of("type,fruit,quantity",
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50");
    private DataReader<String> dataReader;

    @BeforeEach
    void setUp() {
        dataReader = new DataReaderImpl();
    }

    @Test
    void dataFromFile_validFile_ok() {
        String validFile = "src/test/resources/validFile.csv";
        List<String> actualData = dataReader.dataFromFile(validFile);
        assertEquals(EXPECTED_DATA, actualData);
    }

    @Test
    void dataFromFile_emptyFile_ok() {
        final String emptyFile = "src/test/resources/emptyFile.csv";
        List<String> actualData = dataReader.dataFromFile(emptyFile);
        assertEquals(List.of(), actualData);
    }

    @Test
    void dataFromFile_invalidFile_notOk() {
        String invalidFile = "src/test/resources/invalidFile";
        assertThrows(RuntimeException.class, () -> dataReader.dataFromFile(invalidFile));
    }

    @Test
    void dataFromFile_invalidFormat_notOk() {
        String invalidFormatFile = "src/test/resources/invalidFormatFile";
        assertThrows(RuntimeException.class, () -> dataReader.dataFromFile(invalidFormatFile));
    }
}
