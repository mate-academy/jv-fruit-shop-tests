package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.OperationType;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataParser;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CsvDataParserImplTest {
    private static final String COLUMN_SEPARATOR = ",";
    private static final String TEST_PRODUCT = "banana";
    private static final int TEST_AMOUNT = 20;
    private static final String BALANCE_STRING = "b,banana, 20";
    private static final String SUPPLY_STRING = "s,banana, 20";
    private static final String RETURN_STRING = "r,banana, 20";
    private static final String PURCHASE_STRING = "p,banana, 20";
    private static final String EXPECTED_INCORRECT_STRING_MESSAGE
            = "Incorrect string format";

    private static final DataParser parser = new CsvDataParserImpl(COLUMN_SEPARATOR);
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void parse_balanceStringToParse_ok() {
        FruitTransaction expected = new FruitTransaction(OperationType.BALANCE,
                TEST_PRODUCT, TEST_AMOUNT);
        FruitTransaction actual = parser.parse(BALANCE_STRING);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_returnStringToParse_ok() {
        FruitTransaction expected = new FruitTransaction(OperationType.RETURN,
                TEST_PRODUCT, TEST_AMOUNT);
        FruitTransaction actual = parser.parse(RETURN_STRING);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_purchaseStringToParse_ok() {
        FruitTransaction expected = new FruitTransaction(OperationType.PURCHASE,
                TEST_PRODUCT, TEST_AMOUNT);
        FruitTransaction actual = parser.parse(PURCHASE_STRING);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_supplyStringToParse_ok() {
        FruitTransaction expected = new FruitTransaction(OperationType.SUPPLY,
                TEST_PRODUCT, TEST_AMOUNT);
        FruitTransaction actual = parser.parse(SUPPLY_STRING);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_stringContainsMaxIntegerValue_ok() {
        String stringToParse = "s,banana," + Integer.MAX_VALUE;
        FruitTransaction expected = new FruitTransaction(OperationType.SUPPLY,
                TEST_PRODUCT, Integer.MAX_VALUE);
        FruitTransaction actual = parser.parse(stringToParse);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_stringContainsBlanks_ok() {
        FruitTransaction expected = new FruitTransaction(OperationType.SUPPLY,
                TEST_PRODUCT, TEST_AMOUNT);
        FruitTransaction actual = parser.parse("s, banana, 20");
        assertEquals(expected, actual);
    }

    @Test
    public void parse_incorrectTypeToParse_notOk() {
        exceptionRule.expect(RuntimeException.class);
        parser.parse("a,banana,12");
    }

    @Test
    public void parse_incorrectSeparator_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage(EXPECTED_INCORRECT_STRING_MESSAGE);
        parser.parse("s;banana;12");
    }

    @Test
    public void parse_blankString_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage(EXPECTED_INCORRECT_STRING_MESSAGE);
        parser.parse("");
    }

    @Test
    public void parse_nullString_notOk() {
        exceptionRule.expect(RuntimeException.class);
        parser.parse(null);
    }
}
