package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParser;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TransactionParserImplTest {
    private static final List<String> DATA_FROM_FILE = List.of(
            "type,fruit,quantity",
            "b,orange,100",
            "s,orange,50",
            "p,orange,60",
            "r,orange,5");
    private static final List<String> EMPTY_DATA = Collections.emptyList();
    private TransactionParser parser;

    @Before
    public void setUp() {
        parser = new TransactionParserImpl();
    }

    @Test
    public void toTransaction_ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "orange", 100));
        expected.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "orange", 50));
        expected.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "orange", 60));
        expected.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "orange", 5));
        List<FruitTransaction> actual = parser.toTransaction(DATA_FROM_FILE);
        assertEquals(expected, actual);
    }

    @Test
    public void toTransaction_emptyData_notOk() {
        List<FruitTransaction> expected = Collections.emptyList();
        List<FruitTransaction> actual = parser.toTransaction(EMPTY_DATA);
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void toTransaction_nullData_notOk() {
        parser.toTransaction(null);
    }
}
