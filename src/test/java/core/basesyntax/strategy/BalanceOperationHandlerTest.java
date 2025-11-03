package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static FruitDao fruitDao;
    private static BalanceOperationHandler balanceOperationHandler;

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        balanceOperationHandler = new BalanceOperationHandler(fruitDao);
    }

    @BeforeEach
    void setUp() {
        FruitStorage.clearStorage();
    }

    @Test
    void pushToStorage_balanceNewFruit_isOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Type.BALANCE,
                "apple", 13);
        balanceOperationHandler.pushToStorage(fruitTransaction);

        assertEquals(13, fruitDao.get("apple"));
    }

    @Test
    void pushToStorage_balanceToAlreadyExistFruit_isOk() {
        FruitTransaction fruitTransaction1 = new FruitTransaction(FruitTransaction.Type.BALANCE,
                "banana", 5);
        FruitTransaction fruitTransaction2 = new FruitTransaction(FruitTransaction.Type.BALANCE,
                "banana", 2);

        balanceOperationHandler.pushToStorage(fruitTransaction1);
        balanceOperationHandler.pushToStorage(fruitTransaction2);

        assertEquals(7, fruitDao.get("banana"));
    }
}
