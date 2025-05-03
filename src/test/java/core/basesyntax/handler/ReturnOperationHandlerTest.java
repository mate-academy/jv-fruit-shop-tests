package core.basesyntax.handler;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private static Fruit apple;
    private static OperationHandler returnOperationHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeAll
    static void setUp() {
        apple = new Fruit("apple");
        returnOperationHandler = new ReturnOperationHandler();
        fruitTransaction = new FruitTransaction();
    }

    @AfterEach
    void clearStorage() {
        Storage.storage.clear();
    }

    @Test
    void appendProductByReturnHandler_Ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction.setQuantity(33);
        fruitTransaction.setFruit(apple);
        returnOperationHandler.apply(fruitTransaction);
        Integer expected = 33;
        Integer actual = Storage.storage.get(apple);
        Assertions.assertEquals(expected, actual);
    }
}
