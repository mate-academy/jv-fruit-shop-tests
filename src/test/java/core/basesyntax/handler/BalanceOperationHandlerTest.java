package core.basesyntax.handler;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.*;

class BalanceOperationHandlerTest {
    private static Fruit apple;
    private static OperationHandler balanceOperationHandler;
    private static FruitTransaction fruitTransaction;
    @BeforeAll
    static void setUp() {
        apple = new Fruit("apple");
        balanceOperationHandler = new BalanceOperationHandler();
        fruitTransaction = new FruitTransaction();
    }

    @AfterEach
    void clearStorage() {
        Storage.storage.clear();
    }

    @Test
    void appendProductByBalanceHandler_Ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setQuantity(33);
        fruitTransaction.setFruit(apple);
        balanceOperationHandler.apply(fruitTransaction);
        Integer expected = 33;
        Integer actual = Storage.storage.get(apple);
        Assertions.assertEquals(expected, actual);
    }
}
