package core.basesyntax.strategy;

import core.basesyntax.data.Stock;
import org.junit.jupiter.api.Assertions;
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
        Assertions.assertThrows(
                IllegalArgumentException.class, () -> transactionProcessor.process(null));
    }
}
