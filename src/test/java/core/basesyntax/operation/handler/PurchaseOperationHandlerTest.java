package core.basesyntax.operation.handler;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.exception.IllegalStateException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.operation.OperationHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static final int EMPTY_STORAGE_VALUE = 0;
    private static OperationHandler purchaseOperationHandler;
    private static FruitStorageDao fruitStorageDao;
    private static FruitTransaction defaultPurchaseTransaction;

    @BeforeAll
    public static void beforeAll() {
        fruitStorageDao = new FruitStorageDaoImpl();
        purchaseOperationHandler = new PurchaseOperationHandler(fruitStorageDao);
        defaultPurchaseTransaction = new FruitTransaction();
        defaultPurchaseTransaction.setFruit("apple");
        defaultPurchaseTransaction.setOperation(Operation.PURCHASE);
        defaultPurchaseTransaction.setAmount(100);
    }

    @Test
    public void calculateQuantity_purchaseEmptyStorage_notOk() {
        fruitStorageDao.updateFruitQuantity("apple", 0);
        assertThrows(IllegalStateException.class,
                () -> purchaseOperationHandler.calculateQuantity(defaultPurchaseTransaction));
    }

    @Test
    public void calculateQuantity_purchaseNegativeBalance() {
        fruitStorageDao.updateFruitQuantity("apple", 50);
        assertThrows(IllegalStateException.class,
                () -> purchaseOperationHandler.calculateQuantity(defaultPurchaseTransaction));
    }
}
