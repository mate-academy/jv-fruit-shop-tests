package core.basesyntax.model;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OperationTest {

    @Test
    void operationBalanceExist_ok() {
        Assertions.assertDoesNotThrow(() -> {
            Operation.getOperationHandler("b");
        });
    }

    @Test
    void operationReturnExist_ok() {
        Assertions.assertDoesNotThrow(() -> {
            Operation.getOperationHandler("r");
        });
    }

    @Test
    void operationSupplyExist_ok() {
        Assertions.assertDoesNotThrow(() -> {
            Operation.getOperationHandler("s");
        });
    }

    @Test
    void operationPurchaseExist_ok() {
        Assertions.assertDoesNotThrow(() -> {
            Operation.getOperationHandler("p");
        });
    }

    @Test
    void operationAddNotExist_notOk() {
        Assertions.assertThrows(NoSuchElementException.class,() -> {
            Operation.getOperationHandler("a");
        });
    }

    @Test
    void operationDeleteNotExist_notOk() {
        Assertions.assertThrows(NoSuchElementException.class,() -> {
            Operation.getOperationHandler("delete");
        });
    }
}
