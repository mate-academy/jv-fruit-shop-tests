package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class RowFruitTransactionParserTest {
    private static final FruitTransaction EXPECTED_RETURN_TRANSACTION
            = new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 40);
    private static final FruitTransaction EXPECTED_SUPPLY_TRANSACTION
            = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "strawberry", 30);
    private static final FruitTransaction EXPECTED_PURCHASE_TRANSACTION
            = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "orange", 80);
    private static final FruitTransaction EXPECTED_BALANCE_TRANSACTION
            = new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 20);
    private static final String[] RETURN_VALUES = new String[] {"r", "banana", "40"};
    private static final String[] SUPPLY_VALUES = new String[] {"s", "strawberry", "30"};
    private static final String[] PURCHASE_VALUES = new String[] {"p", "orange", "80"};
    private static final String[] BALANCE_VALUES = new String[] {"b", "apple", "20"};
    private static final String[] WRONG_SIZE_VALUES = new String[] {"p", "orange", "sweet", "80"};
    private static final String[] NULL_VALUES = new String[] {null, null, null};

    private FruitTransactionParser parser;

    @Before
    public void before() {
        parser = new RowFruitTransactionParser();
    }

    @Test
    public void parse_regularValue_ok() {
        FruitTransaction balanceTransaction = parser.parse(BALANCE_VALUES);
        assertEquals(balanceTransaction + " expected, but was " + balanceTransaction + "!",
                EXPECTED_BALANCE_TRANSACTION, balanceTransaction);
    }

    @Test
    public void parse_balanceValue_ok() {
        FruitTransaction balanceTransaction = parser.parse(BALANCE_VALUES);
        assertEquals(FruitTransaction.Operation.BALANCE
                        + " expected, but was " + balanceTransaction.getOperation() + "!",
                FruitTransaction.Operation.BALANCE, balanceTransaction.getOperation());
    }

    @Test
    public void parse_returnValue_ok() {
        FruitTransaction returnTransaction = parser.parse(RETURN_VALUES);
        assertEquals(FruitTransaction.Operation.RETURN
                        + " expected, but was " + returnTransaction.getOperation() + "!",
                FruitTransaction.Operation.RETURN, returnTransaction.getOperation());
    }

    @Test
    public void parse_supplyValue_ok() {
        FruitTransaction supplyTransaction = parser.parse(SUPPLY_VALUES);
        assertEquals(FruitTransaction.Operation.SUPPLY
                        + " expected, but was " + supplyTransaction.getOperation() + "!",
                FruitTransaction.Operation.SUPPLY, supplyTransaction.getOperation());
    }

    @Test
    public void parse_purchaseValue_ok() {
        FruitTransaction purchaseTransaction = parser.parse(PURCHASE_VALUES);
        assertEquals(FruitTransaction.Operation.PURCHASE
                        + " expected, but was " + purchaseTransaction.getOperation() + "!",
                FruitTransaction.Operation.PURCHASE, purchaseTransaction.getOperation());
    }

    @Test
    public void parse_collection_ok() {
        List<String[]> strings = new ArrayList<>();
        strings.add(BALANCE_VALUES);
        strings.add(RETURN_VALUES);
        strings.add(SUPPLY_VALUES);
        strings.add(PURCHASE_VALUES);
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(EXPECTED_BALANCE_TRANSACTION);
        expected.add(EXPECTED_RETURN_TRANSACTION);
        expected.add(EXPECTED_SUPPLY_TRANSACTION);
        expected.add(EXPECTED_PURCHASE_TRANSACTION);
        List<FruitTransaction> actual = parser.parse(strings);
        assertEquals(expected + " expected, but was " + actual,
                expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parse_wrongArraySize_notOk() {
        parser.parse(WRONG_SIZE_VALUES);
    }

    @Test
    public void parse_nullValue_ok() {
        String[] s = null;
        FruitTransaction actual = parser.parse(s);
        assertEquals("Null expected but was " + actual,
                null, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parse_nullElementValue_notOk() {
        parser.parse(NULL_VALUES);
    }
}
