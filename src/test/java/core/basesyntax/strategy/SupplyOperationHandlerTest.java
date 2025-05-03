package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private SupplyOperationHandler supplyOperationHandler;
    private FruitStorage fruitStorage;

    @BeforeEach
    void setUp() {
        fruitStorage = new FruitStorage();
        supplyOperationHandler = new SupplyOperationHandler(fruitStorage);
    }

    @Test
    void supplyOperationHandler_performOperation_ok() {
        String fruit = "apple";
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);
        transaction.setFruit(fruit);
        transaction.setQuantity(100);

        supplyOperationHandler.performOperation(transaction);

        assertEquals(100, (long) fruitStorage.getFruitInventory().get(fruit));
    }
}
