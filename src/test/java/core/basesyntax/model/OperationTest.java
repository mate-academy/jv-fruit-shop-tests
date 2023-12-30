package core.basesyntax.model;

import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class OperationTest {

    @Test
    void operationBalanceExist_ok() {
        assertDoesNotThrow( () -> {
            Operation.getOperationHandler("b");
        });
    }

    @Test
    void operationReturnExist_ok() {
        assertDoesNotThrow( () -> {
            Operation.getOperationHandler("r");
        });
    }

    @Test
    void operationSupplyExist_ok() {
        assertDoesNotThrow( () -> {
            Operation.getOperationHandler("s");
        });
    }

    @Test
    void operationPurchaseExist_ok() {
        assertDoesNotThrow( () -> {
            Operation.getOperationHandler("p");
        });
    }

    @Test
    void operationAddNotExist_notOk() {
        assertThrows(NoSuchElementException.class,() -> {
            Operation.getOperationHandler("a");
        });
    }

    @Test
    void operationDeleteNotExist_notOk() {
        assertThrows(NoSuchElementException.class,() -> {
            Operation.getOperationHandler("delete");
        });
    }
}
