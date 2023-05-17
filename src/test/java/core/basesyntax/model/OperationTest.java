package core.basesyntax.model;

import static core.basesyntax.model.FruitTransaction.Operation;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class OperationTest {

    @Test
    void getByCode_validCode_Ok() {
        FruitTransaction.Operation actual1 = Operation.getOperationByCode("b");
        Operation expected1 = Operation.BALANCE;
        assertEquals(expected1, actual1);

        Operation actual2 = Operation.getOperationByCode("s");
        Operation expected2 = Operation.SUPPLY;
        assertEquals(expected2, actual2);

        Operation actual3 = Operation.getOperationByCode("p");
        Operation expected3 = Operation.PURCHASE;
        assertEquals(expected3, actual3);

        Operation actual4 = Operation.getOperationByCode("r");
        Operation expected4 = Operation.RETURN;
        assertEquals(expected4, actual4);
    }
}
