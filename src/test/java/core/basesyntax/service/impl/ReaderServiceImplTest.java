package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.ReaderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {
    private ReaderService readerService;

    @BeforeEach
    void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void read_wrongFilePathTrowsException_Ok() {
        String wrongFilePath = "/src/main/java/wrongPath";

        assertThrows(RuntimeException.class, () -> readerService.read(wrongFilePath));
    }

    @Test
    void read_rightPath_Ok() {
        String filePath = "src/main/resources/file.csv";

        assertDoesNotThrow(() -> readerService.read(filePath));
    }
}
