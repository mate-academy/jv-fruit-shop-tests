package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private static FruitDao fruitDao;
    private static SupplyOperationHandler supplyOperationHandler;

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        supplyOperationHandler = new SupplyOperationHandler(fruitDao);
    }

    @BeforeEach
    void setUp() {
        FruitStorage.clearStorage();
    }

    @Test
    void pushToStorage_supplyFruit_isOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Type.SUPPLY,
                "apple", 15);
        supplyOperationHandler.pushToStorage(fruitTransaction);

        assertEquals(15, fruitDao.get("apple"));
    }

    @Test
    void pushToStorage_supplyToAlreadyExistFruit_isOk() {
        FruitTransaction fruitTransaction1 = new FruitTransaction(FruitTransaction.Type.SUPPLY,
                "banana", 7);
        FruitTransaction fruitTransaction2 = new FruitTransaction(FruitTransaction.Type.SUPPLY,
                "banana", 4);

        supplyOperationHandler.pushToStorage(fruitTransaction1);
        supplyOperationHandler.pushToStorage(fruitTransaction2);

        assertEquals(11, fruitDao.get("banana"));
    }
}
