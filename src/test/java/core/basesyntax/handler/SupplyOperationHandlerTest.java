package core.basesyntax.handler;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private final Fruit apple = new Fruit("apple");
    private final OperationHandler supplyOperationHandler = new SupplyOperationHandler();
    private final FruitTransaction fruitTransaction = new FruitTransaction();

    @Test
    void appendProductBySupplyHandler_Ok() {
        Storage.storage.clear();
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setQuantity(33);
        fruitTransaction.setFruit(apple);
        supplyOperationHandler.apply(fruitTransaction);
        Integer expected = 33;
        Integer actual = Storage.storage.get(apple);
        Assertions.assertEquals(expected, actual);
    }
}
