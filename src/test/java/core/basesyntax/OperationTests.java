package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.util.Operation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationTests {

    @BeforeEach
    void setUp() {
    }

    @Test
    void operation_getCode_notOk() {
        String operationCodeWrong = "a";
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Operation.getCode(operationCodeWrong);
        });
    }

    @Test
    void fruitTransaction_setOperation_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.BALANCE, "banana", 100);
        fruitTransaction.setOperation(Operation.PURCHASE);
        Operation actual = fruitTransaction.getOperation();
        Assertions.assertEquals(Operation.PURCHASE, actual);
    }

    @AfterEach
    public void afterEachTest() {
    }
}
