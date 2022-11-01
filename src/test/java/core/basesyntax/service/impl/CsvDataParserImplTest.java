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
    private static final DataParser parser = new CsvDataParserImpl(COLUMN_SEPARATOR);
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void parse_balanceStringToParse_ok() {
        String stringToParse = "b,banana,22";
        FruitTransaction expected = new FruitTransaction(OperationType.BALANCE,
                "banana", 22);
        FruitTransaction actual = parser.parse(stringToParse);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_returnStringToParse_ok() {
        String stringToParse = "r,banana,2";
        FruitTransaction expected = new FruitTransaction(OperationType.RETURN,
                "banana", 2);
        FruitTransaction actual = parser.parse(stringToParse);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_purchaseStringToParse_ok() {
        String stringToParse = "p,banana,12";
        FruitTransaction expected = new FruitTransaction(OperationType.PURCHASE,
                "banana", 12);
        FruitTransaction actual = parser.parse(stringToParse);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_supplyStringToParse_ok() {
        String stringToParse = "s,banana,12";
        FruitTransaction expected = new FruitTransaction(OperationType.SUPPLY,
                "banana", 12);
        FruitTransaction actual = parser.parse(stringToParse);
        assertEquals(expected, actual);
    }

    @Test
    public void stringContainsMaxIntegerValue_ok() {
        String stringToParse = "s,banana," + Integer.MAX_VALUE;
        FruitTransaction expected = new FruitTransaction(OperationType.SUPPLY,
                "banana", Integer.MAX_VALUE);
        FruitTransaction actual = parser.parse(stringToParse);
        assertEquals(expected, actual);
    }

    @Test
    public void stringContainsBlanks_ok() {
        String stringToParse = "s, banana, 12";
        FruitTransaction expected = new FruitTransaction(OperationType.SUPPLY,
                "banana", 12);
        FruitTransaction actual = parser.parse(stringToParse);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_incorrectTypeToParse_notOk() {
        exceptionRule.expect(RuntimeException.class);
        String stringToParse = "a,banana,12";
        parser.parse(stringToParse);
    }

    @Test
    public void incorrectSeparator_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Incorrect string format");
        String stringToParse = "s;banana;12";
        parser.parse(stringToParse);
    }

    @Test
    public void blankString_notOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Incorrect string format");
        String stringToParse = "";
        parser.parse(stringToParse);
    }

    @Test
    public void nullString_notOk() {
        exceptionRule.expect(RuntimeException.class);
        parser.parse(null);
    }
}
