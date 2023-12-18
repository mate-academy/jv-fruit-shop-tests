package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

class TransactionParserTest {
    private TransactionParser transactionParser = new TransactionParser();
    private List<String> data = new ArrayList<>();

    @Test
    public void testParseTransactionsEmptyData() {
        List<FruitTransaction> result = transactionParser
                .parseTransactions(Collections.emptyList());
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testParseTransactionsHeaderOnly() {
        data.add("type,fruit,quantity");
        List<FruitTransaction> result = transactionParser.parseTransactions(data);
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void testParseTransactionsSingleTransaction() {
        data.add("type,fruit,quantity");
        data.add("b,apple,10");
        List<FruitTransaction> result = transactionParser.parseTransactions(data);
        FruitTransaction expected = new FruitTransaction("b", "apple", "10");
        assertEquals(Collections.singletonList(expected), result);
    }

    @Test
    public void testParseTransactionsInvalidData() {
        List<String> data = Arrays.asList("BUY,apple", "SELL,banana,20,extra");
        assertThrows(IllegalArgumentException.class,
                () -> transactionParser.parseTransactions(data));
    }
}
