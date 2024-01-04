package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitShopDao;
import core.basesyntax.dao.FruitShopDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerImplTest {
    private OperationHandler operationHandler;
    private FruitShopDao fruitShopDao;
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        Storage.fruitsDB.clear();
        fruitShopDao = new FruitShopDaoImpl();
        operationHandler = new ReturnOperationHandlerImpl(fruitShopDao);
        fruitTransaction = new FruitTransaction(Operation.RETURN, "apple", 100);
    }

    @Test
    void operationPurchaseHavePositiveAmount_ok() {
        Storage.fruitsDB.put("apple", 30);
        operationHandler.operation(fruitTransaction);
        int apple1 = fruitShopDao.getQuantity("apple");
        Assertions.assertEquals(130, apple1);
    }
}
