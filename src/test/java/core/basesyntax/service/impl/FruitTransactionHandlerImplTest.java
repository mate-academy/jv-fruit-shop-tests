package core.basesyntax.service.impl;

import static org.junit.Assert.assertFalse;

import core.basesyntax.db.Storage;
import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.FruitTransactionHandler;
import org.junit.Test;

public class FruitTransactionHandlerImplTest {
    private static final String VALID_DATA_FILENAME = "src/test/java/resources/inputData.csv";
    private final FileReaderService fileReaderService
            = new CsvFileReaderServiceImpl();
    private final FruitTransactionHandler fruitTransactionHandler
            = new FruitTransactionHandlerImpl();

    @Test
    public void handle_validInputValue_isOk() {
        fruitTransactionHandler.handle(Storage.FRUITS,
                fileReaderService.readFromFile(VALID_DATA_FILENAME));
        assertFalse(Storage.FRUITS.isEmpty());
    }

    @Test(expected = NullPointerException.class)
    public void handle_nullInputValue_isNotOk() {
        fruitTransactionHandler.handle(null, null);
    }
}
