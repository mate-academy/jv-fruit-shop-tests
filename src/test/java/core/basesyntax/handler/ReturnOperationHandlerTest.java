package core.basesyntax.handler;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private final Fruit apple = new Fruit("apple");
    private final OperationHandler returnOperationHandler = new BalanceOperationHandler();
    private final FruitTransaction fruitTransaction = new FruitTransaction();

    @Test
    void appendProductByReturnHandler_Ok() {
        Storage.storage.clear();
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction.setQuantity(33);
        fruitTransaction.setFruit(apple);
        returnOperationHandler.apply(fruitTransaction);
        Integer expected = 33;
        Integer actual = Storage.storage.get(apple);
        Assertions.assertEquals(expected, actual);
    }
}
