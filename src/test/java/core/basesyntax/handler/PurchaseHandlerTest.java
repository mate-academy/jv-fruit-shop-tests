package core.basesyntax.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.handler.PurchaseHandler;
import org.junit.jupiter.api.Test;

public class PurchaseHandlerTest {
    
    @Test
    public void handle_PurchaseHandler_ok() {
        FakeTransactionDao dao = new FakeTransactionDao();
        PurchaseHandler purchaseHandler = new PurchaseHandler(dao);
        Transaction transaction = new Transaction(Transaction.Operation.PURCHASE, "banana", 70);
        purchaseHandler.handle(transaction);

        assertEquals("subtract", dao.getLastMethod());
        assertEquals("banana", dao.getFruit());
        assertEquals(70, dao.getQuantity());
    }
}
