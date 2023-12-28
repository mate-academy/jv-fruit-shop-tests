package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private ReturnOperationHandler returnOperationHandler;
    private FruitStorage fruitStorage;

    @BeforeEach
    void setUp() {
        fruitStorage = new FruitStorage();
        returnOperationHandler = new ReturnOperationHandler(fruitStorage);
    }

    @Test
    void returnOperationHandler_performOperation_ok() {
        String fruit = "apple";
        int initialQuantity = 10;
        final int returnQuantity = 5;

        FruitTransaction initialTransaction = new FruitTransaction();
        initialTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        initialTransaction.setFruit(fruit);
        initialTransaction.setQuantity(initialQuantity);

        FruitTransaction returnTransaction = new FruitTransaction();
        returnTransaction.setOperation(FruitTransaction.Operation.RETURN);
        returnTransaction.setFruit(fruit);
        returnTransaction.setQuantity(returnQuantity);

        returnOperationHandler.performOperation(initialTransaction);
        returnOperationHandler.performOperation(returnTransaction);

        int expectedBalance = initialQuantity + returnQuantity;
        assertEquals(expectedBalance, (long) fruitStorage.getFruitInventory().get(fruit));
    }
}
