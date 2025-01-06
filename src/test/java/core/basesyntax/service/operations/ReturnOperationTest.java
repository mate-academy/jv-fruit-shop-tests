package core.basesyntax.service.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private FruitTransaction fruitTransaction = new FruitTransaction();
    private ReturnOperation returnOperation = new ReturnOperation();

    @Test
    void returnOperation_Ok() {
        int quantity = 10;
        returnOperation.doOperation(fruitTransaction, quantity);
        assertEquals(quantity, fruitTransaction.getQuantity());
    }
}
