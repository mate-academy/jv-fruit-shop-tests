package core.basesyntax.strategy;

import static org.junit.Assert.assertThrows;

import core.basesyntax.data.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionProcessorImplTest {
    private Stock stock;
    private TransactionProcessor transactionProcessor;

    @BeforeEach
    void setUp() {
        stock = new Stock();
        transactionProcessor = new TransactionProcessorImpl(stock);
        stock.clear();
    }

    @Test
    void nullListTransactions_notOk() {
        assertThrows(
                IllegalArgumentException.class, () -> transactionProcessor.process(null));
    }
}
