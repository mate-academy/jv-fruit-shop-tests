package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private static FruitDao fruitDao;
    private static ReturnOperationHandler returnOperationHandler;

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        returnOperationHandler = new ReturnOperationHandler(fruitDao);
    }

    @BeforeEach
    void setUp() {
        FruitStorage.clearStorage();
    }

    @Test
    void pushToStorage_returnFruit_isOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Type.RETURN,
                "apple", 14);
        returnOperationHandler.pushToStorage(fruitTransaction);

        assertEquals(14, fruitDao.get("apple"));
    }

    @Test
    void pushToStorage_returnToAlreadyExistFruit_isOk() {
        FruitTransaction fruitTransaction1 = new FruitTransaction(FruitTransaction.Type.RETURN,
                "banana", 6);
        FruitTransaction fruitTransaction2 = new FruitTransaction(FruitTransaction.Type.RETURN,
                "banana", 3);

        returnOperationHandler.pushToStorage(fruitTransaction1);
        returnOperationHandler.pushToStorage(fruitTransaction2);

        assertEquals(9, fruitDao.get("banana"));
    }
}
