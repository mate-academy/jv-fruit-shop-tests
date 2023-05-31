package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionProcessor;
import core.basesyntax.service.impl.TransactionProcessorImpl;
import core.basesyntax.strorage.FruitStorage;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionProcessorTest {
    private static TransactionProcessor transactionProcessor;

    @BeforeClass
    public static void setUp() {
        transactionProcessor = new TransactionProcessorImpl();
        FruitStorage.fruits.clear();
    }

    @Test
    public void process_validCase_ok() {
        List<FruitTransaction> fruitTransactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 15),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 10));
        transactionProcessor.process(fruitTransactions);
        int actual = FruitStorage.fruits.get("banana");
        int expected = 5;
        assertEquals("Wrong amount banana: ", expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void process_nullValue_notOk() {
        List<FruitTransaction> fruitTransactions = List.of(null, null);
        transactionProcessor.process(fruitTransactions);
    }
}
