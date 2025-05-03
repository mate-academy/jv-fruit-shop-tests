package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyHandlerImplTest {
    private static OperationHandler operationHandler;
    private static FruitDao fruitDao;
    private FruitTransaction fruit;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new SupplyHandlerImpl();
        fruitDao = new FruitDaoImpl();
    }

    @Test
    void enoughFruitsInStorage_Ok() {
        fruit = createFruitTransaction("s", "apple", 10);
        fruitDao.add("apple", 30);
        int expectedQuantity = 40;
        operationHandler.apply(fruit);
        int actualQuantity = fruitDao.get(fruit.getFruit());
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void negativeQuantity_NotOk() {
        fruit = createFruitTransaction("s", "apple", -10);
        assertThrows(RuntimeException.class, () -> operationHandler.apply(fruit));
    }

    private FruitTransaction createFruitTransaction(String operation, String fruit, int quantity) {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(Operation.getOperationFromCode(operation));
        transaction.setFruit(fruit);
        transaction.setQuantity(quantity);
        return transaction;
    }
}
