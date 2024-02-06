package core.basesyntax.transaction;

import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransactionProcessorImplTest {
    private TransactionProcessor transactionProcessor;
    private final List<FruitTransaction> list = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        transactionProcessor = new TransactionProcessorImpl();
    }

    @AfterEach
    public void tearDown() throws IOException {
        list.clear();
        Storage.storage.clear();
    }

    @Test
    void processTransactions_NullContent_ThrowException() {
        assertThrows(RuntimeException.class, () -> transactionProcessor.processTransactions(null));
    }

    @Test
    void processTransactions_EmptyTransaction_ThrowException() {
        FruitTransaction transaction = new FruitTransaction(null, null, 0);
        list.add(transaction);
        assertThrows(RuntimeException.class, () -> transactionProcessor.processTransactions(list));
    }
}
