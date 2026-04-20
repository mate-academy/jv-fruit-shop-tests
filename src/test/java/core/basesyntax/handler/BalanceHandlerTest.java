package core.basesyntax.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.handler.BalanceHandler;
import org.junit.jupiter.api.Test;

public class BalanceHandlerTest {
    @Test
    public void handle_BalanceHandler_ok() {
        FakeTransactionDao dao = new FakeTransactionDao();
        BalanceHandler balanceHandler = new BalanceHandler(dao);
        Transaction transaction = new Transaction(Transaction.Operation.BALANCE, "banana", 120);
        balanceHandler.handle(transaction);

        assertEquals("set", dao.getLastMethod());
        assertEquals("banana", dao.getFruit());
        assertEquals(120, dao.getQuantity());
    }
}
