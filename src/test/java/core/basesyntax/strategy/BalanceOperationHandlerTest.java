package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private BalanceOperationHandler balanceOperationHandler;
    private FruitStorage fruitStorage;

    @BeforeEach
    void setUp() {
        fruitStorage = new FruitStorage();
        balanceOperationHandler = new BalanceOperationHandler(fruitStorage);
    }

    @Test
    void balanceOperationHandler_performOperation_ok() {
        String fruit = "apple";
        int quantity = 5;
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        transaction.setFruit(fruit);
        transaction.setQuantity(quantity);

        balanceOperationHandler.performOperation(transaction);

        assertEquals(5, (int) fruitStorage.getFruitInventory().get(fruit));
    }
}
