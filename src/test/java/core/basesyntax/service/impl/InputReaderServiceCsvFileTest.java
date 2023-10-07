package core.basesyntax.service.impl;

import static core.basesyntax.Base.FILE_WITH_WRONG_OPERATION;
import static core.basesyntax.Base.FILE_WITH_WRONG_QUANTITY;
import static core.basesyntax.Base.UNKNOWN_TXT;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.InputReaderService;
import org.junit.jupiter.api.Test;

class InputReaderServiceCsvFileTest {

    @Test
    void readInput_wrongFileName_notOk() {
        InputReaderService csvFileReaderService = new InputReaderServiceCsvFile();
        assertThrows(RuntimeException.class, () -> csvFileReaderService.readInput(UNKNOWN_TXT),
                "Must throw RuntimeException");
        assertThrows(RuntimeException.class, () -> csvFileReaderService.readInput(null),
                "Must throw RuntimeException");
    }

    @Test
    void readInput_wrongOperation_notOk() {
        InputReaderService csvFileReaderService = new InputReaderServiceCsvFile();
        assertThrows(RuntimeException.class,
                () -> csvFileReaderService.readInput(FILE_WITH_WRONG_OPERATION),
                "Must throw RuntimeException");
    }

    @Test
    void readInput_wrongQuantity_notOk() {
        InputReaderService csvFileReaderService = new InputReaderServiceCsvFile();
        assertThrows(RuntimeException.class,
                () -> csvFileReaderService.readInput(FILE_WITH_WRONG_QUANTITY),
                "Must throw RuntimeException");
    }
}
