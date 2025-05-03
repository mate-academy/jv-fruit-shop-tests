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

class PurchaseOperationHandlerTest {
    public static final String DEFAULT_FRUIT = "banana";
    public static final int DEFAULT_QUANTITY = 100;
    public static final int DEFAULT_PURCHASE_QUANTITY = 50;

    public static final Operation DEFAULT_OPERATION = Operation.PURCHASE;
    private OperationHandler purchaseHandler;
    private final FruitTransactionValidation validator = new FruitTransactionValidationImpl();
    private final StorageDao storageDao = new StorageDaoImpl(validator);

    @BeforeEach
    void setUp() {
        purchaseHandler = new PurchaseOperationHandler(storageDao);
        Storage.storage.put(DEFAULT_FRUIT, DEFAULT_QUANTITY);
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void handleValidFruit_Ok() {
        FruitTransaction transaction = new FruitTransaction(DEFAULT_OPERATION,
                DEFAULT_FRUIT, DEFAULT_PURCHASE_QUANTITY);
        assertDoesNotThrow(() -> purchaseHandler.handle(transaction));
    }

    @Test
    void handleNullFruit_NotOk() {
        assertThrows(FruitTransactionException.class,
                () -> purchaseHandler.handle(null));
    }

    @Test
    void handleNotAvailablePurchase_NotOk() {
        int greaterThanAvailable = 150;
        FruitTransaction transaction = new FruitTransaction(DEFAULT_OPERATION,
                DEFAULT_FRUIT, greaterThanAvailable);
        assertThrows(FruitTransactionException.class,
                () -> purchaseHandler.handle(transaction));
    }
}
