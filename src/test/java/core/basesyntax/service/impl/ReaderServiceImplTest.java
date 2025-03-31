package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderServiceImplTest {

    private ReaderServiceImpl readerService;

    @BeforeEach
    void setUp() {
        readerService = new ReaderServiceImpl();
    }

    @Test
    void fileIsNull_notOk() {
        assertThrows(
                IllegalArgumentException.class, () -> readerService.readTheReport(null));
    }

    @Test
    void fileIsEmpty_notOk() {
        assertThrows(IllegalArgumentException.class, () -> readerService.readTheReport(""));
    }
}
