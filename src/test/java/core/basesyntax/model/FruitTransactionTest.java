package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction.OperationType;
import org.junit.Test;

public class FruitTransactionTest {

    @Test
    public void getOperationTypeByCode_allValidBalance_Ok() {
        assertAll(
                () -> assertEquals(OperationType.BALANCE,
                            OperationType.getOperationTypeByCode("b"), "it must be BALANCE for b"),
                () -> assertEquals(OperationType.PURCHASE,
                            OperationType.getOperationTypeByCode("p"), "it must be PURCHASE for p"),
                () -> assertEquals(OperationType.RETURN,
                            OperationType.getOperationTypeByCode("r"), "it must be RETURN for r"),
                () -> assertEquals(OperationType.SUPPLY,
                            OperationType.getOperationTypeByCode("s"), "it must be SUPPLY for s")
        );
    }
}
