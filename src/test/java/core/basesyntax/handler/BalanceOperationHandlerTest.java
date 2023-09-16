package core.basesyntax.handler;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private final Fruit apple = new Fruit("apple");
    private final OperationHandler balanceOperationHandler = new BalanceOperationHandler();
    private final FruitTransaction fruitTransaction = new FruitTransaction();

    @Test
    void appendProductByBalanceHandler_Ok() {
        Storage.storage.clear();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setQuantity(33);
        fruitTransaction.setFruit(apple);
        balanceOperationHandler.apply(fruitTransaction);
        Integer expected = 33;
        Integer actual = Storage.storage.get(apple);
        Assertions.assertEquals(expected, actual);
    }
}
