package core.basesyntax.service.operations;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SupplyOperationTest {
    private FruitTransaction fruitTransaction = new FruitTransaction();
    private SupplyOperation supplyOperation = new SupplyOperation();

    @Test
    void supplyOperation_Ok() {
        int quantity = 10;
        supplyOperation.doOperation(fruitTransaction, quantity);
        assertEquals(quantity, fruitTransaction.getQuantity());
    }

}