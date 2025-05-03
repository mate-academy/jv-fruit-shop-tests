package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TransactionParserImplTest {
    private static final List<String> DATA_FROM_FILE = List.of(
            "type,fruit,quantity",
            "b,banana,20",
            "s,banana,100",
            "p,banana,13",
            "r,banana,10");
    private TransactionParser parser;

    @Before
    public void setUp() {
        parser = new TransactionParserImpl();
    }

    @Test
    public void toTransaction_Ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20));
        expected.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100));
        expected.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13));
        expected.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 10));
        List<FruitTransaction> actual = parser.toTransaction(DATA_FROM_FILE);
        assertEquals(expected, actual);
    }
}
