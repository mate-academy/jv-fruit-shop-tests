package core.basesyntax.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.handler.ReturnHandler;
import org.junit.jupiter.api.Test;

public class ReturnHandlerTest {
    @Test
    public void handle_ReturnHandler_ok() {
        FakeTransactionDao dao = new FakeTransactionDao();
        ReturnHandler handler = new ReturnHandler(dao);
        Transaction transaction = new Transaction(Transaction.Operation.RETURN, "apple", 10);
        handler.handle(transaction);

        assertEquals("add", dao.getLastMethod());
        assertEquals("apple", dao.getFruit());
        assertEquals(10, dao.getQuantity());
    }
}
