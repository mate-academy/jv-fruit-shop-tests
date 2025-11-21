package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static FruitDao fruitDao;
    private static PurchaseOperationHandler purchaseOperationHandler;

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        purchaseOperationHandler = new PurchaseOperationHandler(fruitDao);
    }

    @BeforeEach
    void setUp() {
        FruitStorage.clearStorage();
    }

    @Test
    void pushToStorage_purchaseEnoughFruit_isOk() {
        fruitDao.add("apple", 50, Integer::sum);

        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Type.PURCHASE,
                "apple", 10);
        purchaseOperationHandler.pushToStorage(fruitTransaction);

        assertEquals(40, fruitDao.get("apple"));
    }

    @Test
    void pushToStorage_purchaseNotEnough_throwException() {
        fruitDao.add("banana", 5, Integer::sum);
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Type.PURCHASE,
                "banana", 15);

        assertThrows(RuntimeException.class,
                () -> purchaseOperationHandler.pushToStorage(fruitTransaction));
    }
}
