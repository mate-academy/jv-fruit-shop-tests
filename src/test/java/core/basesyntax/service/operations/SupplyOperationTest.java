package core.basesyntax.service.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

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
