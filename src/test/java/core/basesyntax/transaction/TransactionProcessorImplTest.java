package core.basesyntax.transaction;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertThrows;

class TransactionProcessorImplTest {
    private TransactionProcessor transactionProcessor;
    List<FruitTransaction> list = new ArrayList<>();

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
    void testWriteToStorageWithNullContent() {
        assertThrows(RuntimeException.class, () -> transactionProcessor.processTransactions(null));
    }

    @Test
    void testWriteToStorageOneElement() {
        FruitTransaction transaction = new FruitTransaction(null, null, 0);
        list.add(transaction);
        assertThrows(RuntimeException.class, () -> transactionProcessor.processTransactions(list));
    }
}