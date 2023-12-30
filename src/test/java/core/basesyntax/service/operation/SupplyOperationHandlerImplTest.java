package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitShopDao;
import core.basesyntax.dao.FruitShopDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerImplTest {
    private OperationHandler operationHandler;
    private FruitShopDao fruitShopDao;

    @BeforeEach
    void setUp() {
        Storage.fruitsDB.clear();
        fruitShopDao = new FruitShopDaoImpl();
        operationHandler = new SupplyOperationHandlerImpl(fruitShopDao);
    }

    @Test
    void operationPurchaseHavePositiveAmount_ok() {
        Storage.fruitsDB.put("apple", 200);
        FruitTransaction apple = new FruitTransaction(Operation.BALANCE, "apple", 100);
        operationHandler.operation(apple);
        int apple1 = fruitShopDao.getQuantity("apple");
        Assertions.assertEquals(300, apple1);
    }
}
