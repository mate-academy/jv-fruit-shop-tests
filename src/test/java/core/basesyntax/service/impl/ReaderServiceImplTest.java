package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    @Test
    void read_wrongFilePathTrowsException_Ok() {
        var readerService = new ReaderServiceImpl();
        String wrongFilePath = "/src/main/java/wrongPath";

        assertThrows(RuntimeException.class, () -> readerService.read(wrongFilePath));
    }

    @Test
    void read_rightPath_Ok() {
        var readerService = new ReaderServiceImpl();
        String filePath = "src/main/resources/file.csv";

        assertDoesNotThrow(() -> readerService.read(filePath));
    }
}
