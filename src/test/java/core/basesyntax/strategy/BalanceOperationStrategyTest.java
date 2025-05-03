package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.fruitstorge.FruitStorage;
import core.basesyntax.fruittransaction.FruitTransaction;
import core.basesyntax.operations.Operation;
import org.junit.jupiter.api.Test;

class BalanceOperationStrategyTest {

    @Test
    void handle() {
        OperationHandler operationHandler = new BalanceOperationStrategy();
        String fruitName = "apple";
        int initialQuantity = 5;
        int transactionAmount = 3;
        FruitStorage.fruitStorage.put(fruitName, initialQuantity);
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.BALANCE, fruitName, transactionAmount);
        operationHandler.handle(fruitTransaction);
        int expectedQuantity = initialQuantity + transactionAmount;
        assertEquals(expectedQuantity, FruitStorage.fruitStorage.get(fruitName));
    }
}
