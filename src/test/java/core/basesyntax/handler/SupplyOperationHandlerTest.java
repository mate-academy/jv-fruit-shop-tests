package core.basesyntax.handler;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private static Fruit apple;
    private static OperationHandler supplyOperationHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeAll
    static void setUp() {
        apple = new Fruit("apple");
        supplyOperationHandler = new SupplyOperationHandler();
        Storage.storage.clear();
        fruitTransaction = new FruitTransaction();
    }

    @AfterEach
    void clearStorage() {
        Storage.storage.clear();
    }

    @Test
    void appendProductBySupplyHandler_Ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setQuantity(33);
        fruitTransaction.setFruit(apple);
        supplyOperationHandler.apply(fruitTransaction);
        Integer expected = 33;
        Integer actual = Storage.storage.get(apple);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void appendProductToExistFruit_Ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setQuantity(33);
        fruitTransaction.setFruit(apple);
        Storage.storage.put(apple,22);
        supplyOperationHandler.apply(fruitTransaction);
        Integer expected = 55;
        Integer actual = Storage.storage.get(apple);
        Assertions.assertEquals(expected, actual);
    }
}
