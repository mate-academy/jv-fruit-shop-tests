package core.basesyntax.service.handler;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

class OperationHandlerTest {
    @Test
    void handleTransaction_ValidTransaction_Success() {
        OperationHandler handler = new CustomOperationHandler();
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("apple");
        transaction.setQuantity(10);
        handler.handleTransaction(transaction);
    }

    @Test
    void handleTransaction_InvalidTransaction_ThrowsException() {
        OperationHandler handler = new CustomOperationHandler();
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("apple");
        transaction.setQuantity(-10);
        assertThrows(RuntimeException.class, () -> handler.handleTransaction(transaction));
    }

    private static class CustomOperationHandler implements OperationHandler {
        @Override
        public void handleTransaction(FruitTransaction transaction) {
            if (transaction.getQuantity() < 0) {
                throw new RuntimeException("Invalid quantity!");
            }
        }
    }
}
