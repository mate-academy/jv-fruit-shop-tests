package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitShopDao;
import core.basesyntax.dao.FruitShopDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerImplTest {
    private OperationHandler operationHandler;
    private FruitShopDao fruitShopDao;
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        Storage.fruitsDB.clear();
        fruitShopDao = new FruitShopDaoImpl();
        operationHandler = new PurchaseOperationHandlerImpl(fruitShopDao);
        fruitTransaction = new FruitTransaction(Operation.PURCHASE, "apple", 30);
    }

    @Test
    void operationPurchaseHavePositiveAmount_ok() {
        Storage.fruitsDB.put("apple", 200);
        operationHandler.operation(fruitTransaction);
        int apple1 = fruitShopDao.getQuantity("apple");
        Assertions.assertEquals(170, apple1);
    }

    @Test
    void operationPurchaseHaveNegativeAmount_notOk() {
        Storage.fruitsDB.put("banana", 30);
        fruitTransaction.setOperation(Operation.PURCHASE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(50);
        Assertions.assertThrows(RuntimeException.class, () -> {
            operationHandler.operation(fruitTransaction);
        });
    }
}
