package core.basesyntax.handler;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import core.basesyntax.checker.EntryValidator;
import core.basesyntax.db.InMemoryStorage;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.TransactionDao;
import core.basesyntax.service.impl.TransactionDaoImpl;
import core.basesyntax.strategy.handler.BalanceHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnHandlerTest {
    private Storage storage;
    private EntryValidator entryValidator;
    private TransactionDao dao;

    @BeforeEach
    public void setUp() {
        storage = new InMemoryStorage();
        entryValidator = new EntryValidator(storage);
        dao = new TransactionDaoImpl(storage, entryValidator);
    }
    
    @Test
    public void handle_returnTransaction_doesntThrowException() {
        BalanceHandler balanceHandler = new BalanceHandler(dao);
        Transaction transaction = new Transaction(Transaction.Operation.RETURN, "pineapple", 30);

        assertDoesNotThrow(() -> balanceHandler.handle(transaction));
    }
}
