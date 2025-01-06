package core.basesyntax.service.operations;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BalanceOperationTest {
    private FruitTransaction fruitTransaction = new FruitTransaction();
    private BalanceOperation balanceOperation = new BalanceOperation();

    @Test
    void balanceOperation_Ok() {
        int quantity = 10;
        balanceOperation.doOperation(fruitTransaction, quantity);
        assertEquals(quantity, fruitTransaction.getQuantity());
    }
}