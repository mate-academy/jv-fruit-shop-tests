package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.models.FruitTransfer;
import core.basesyntax.models.InvalidDataException;
import core.basesyntax.models.Operation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private static final String FRUIT = "pear";
    private static final int QUANTITY = 10;
    private static final int NEGATIVE_QUANTITY = -10;
    private static final int EXCESSIVE_QUANTITY = 20;
    private static OperationHandler purchaseHandler;
    private static StorageDao storageDao;
    private FruitTransfer fruitTransfer;
    private OperationHandler balanceHandler;

    @BeforeAll
    static void beforeAll() {
        purchaseHandler = new PurchaseHandler();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        balanceHandler = new BalanceHandler();
        balanceHandler.performOperation(new FruitTransfer(Operation.BALANCE, FRUIT, QUANTITY));
    }

    @AfterEach
    void tearDown() {
        Storage.getStoredFruits().clear();
    }

    @Test
    void performOperation_purchaseFruit_Ok() {
        fruitTransfer = new FruitTransfer(Operation.PURCHASE, FRUIT, QUANTITY);
        purchaseHandler.performOperation(fruitTransfer);
        int expected = 0;
        int actual = storageDao.getStoredQuantity(fruitTransfer.getFruit());
        assertEquals(expected, actual);
    }

    @Test
    void performOperation_putNegativePurchase_NotOk() {
        fruitTransfer = new FruitTransfer(Operation.PURCHASE, FRUIT, NEGATIVE_QUANTITY);
        assertThrows(InvalidDataException.class, () ->
                purchaseHandler.performOperation(fruitTransfer));
    }

    @Test
    void performOperation_purchaseMoreThanStored_NotOk() {
        fruitTransfer = new FruitTransfer(Operation.PURCHASE, FRUIT, EXCESSIVE_QUANTITY);
        assertThrows(InvalidDataException.class, () ->
                purchaseHandler.performOperation(fruitTransfer));
    }
}
