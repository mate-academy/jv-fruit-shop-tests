package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.service.ReaderService;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private ReaderService readerService = new ReaderServiceImpl();
    
    @Test
    void readData_validFile_ok() {
        String fileName = "src/test/resources/validValues_ok.csv";
        int actual = readerService.readData(fileName).size();
        int expected = 11;
        assertEquals(expected, actual);
    }

    @Test
    void readData_invalidFilePath_notOk() {
        String fileName = "src/test/resoures/validValues_ok.csv";
        assertThrows(RuntimeException.class, () -> {
            readerService.readData(fileName);
        });
    }

    @Test
    void readData_nullInputParameter_notOk() {
        assertThrows(RuntimeException.class, () -> {
            readerService.readData(null);
        });
    }
}
