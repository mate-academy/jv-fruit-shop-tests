package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.FruitTransactionException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitTransactionValidation;
import core.basesyntax.service.impl.FruitTransactionValidationImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private OperationHandler supplyHandler;
    private final FruitTransactionValidation validator = new FruitTransactionValidationImpl();
    private final StorageDao storageDao = new StorageDaoImpl(validator);

    @BeforeEach
    void setUp() {
        supplyHandler = new ReturnOperationHandler(storageDao);
        Storage.storage.put("banana", 100);
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void handleValidFruit_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.SUPPLY, "banana", 100);
        assertDoesNotThrow(() -> supplyHandler.handle(transaction));
    }

    @Test
    void handleNullFruit_NotOk() {
        assertThrows(FruitTransactionException.class,
                () -> supplyHandler.handle(null));
    }
}
