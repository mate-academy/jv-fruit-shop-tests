package core.basesyntax.strategy.read;

import static core.basesyntax.model.DataSourceType.CsvFile;
import static core.basesyntax.model.DataSourceType.UNKNOWN;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.InputReaderService;
import core.basesyntax.service.impl.InputReaderServiceCsvFile;
import org.junit.jupiter.api.Test;

class InputReaderStrategyImplTest {
    private final InputReaderStrategy inputReaderStrategy = new InputReaderStrategyImpl();

    @Test
    void get_isOk() {
        InputReaderService csvFileReaderService = inputReaderStrategy.get(CsvFile);
        assertTrue(csvFileReaderService instanceof InputReaderServiceCsvFile,
                "csvFileReaderService must be instance of "
                        + InputReaderServiceCsvFile.class.getSimpleName());
    }

    @Test
    void get_wrongDataSourceType_notOk() {
        InputReaderStrategy inputReaderStrategy = new InputReaderStrategyImpl();
        assertThrows(RuntimeException.class, () -> inputReaderStrategy.get(UNKNOWN),
                "Must throws RuntimeException.");
        assertThrows(RuntimeException.class, () -> inputReaderStrategy.get(null),
                "Must throws RuntimeException.");
    }
}
