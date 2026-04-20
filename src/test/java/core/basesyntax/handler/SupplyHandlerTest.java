package core.basesyntax.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.handler.SupplyHandler;
import org.junit.jupiter.api.Test;

public class SupplyHandlerTest {
    @Test
    public void handle_SupplyHandler_ok() {
        FakeTransactionDao dao = new FakeTransactionDao();
        SupplyHandler handler = new SupplyHandler(dao);
        Transaction transaction = new Transaction(Transaction.Operation.SUPPLY, "apple", 10);
        handler.handle(transaction);

        assertEquals("add", dao.getLastMethod());
        assertEquals("apple", dao.getFruit());
        assertEquals(10, dao.getQuantity());
    }
}
