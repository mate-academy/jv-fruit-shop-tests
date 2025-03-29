package core.basesyntax.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReaderServiceImplTest {

    ReaderServiceImpl readerService;

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