package core.basesyntax.utils;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CsvParserUtilTest {
    private static final String INVALID_LINE_FORMAT = "The line has wrong format";
    private static final String BANANA = "banana";
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    private CsvParserUtil csvParserUtil;

    @Before
    public void setUp() {
        csvParserUtil = new CsvParserUtil();
    }

    @Test
    public void extractTransaction_balanceType_ok() {
        String line = "b,banana,20";
        FruitTransaction actual = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                BANANA, 20);
        FruitTransaction expected = csvParserUtil.extractTransaction(line);
        assertEquals(expected, actual);
    }

    @Test
    public void extractTransaction_purchaseType_ok() {
        String line = "p,banana,20";
        FruitTransaction actual = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                BANANA, 20);
        FruitTransaction expected = csvParserUtil.extractTransaction(line);
        assertEquals(expected, actual);
    }

    @Test
    public void extractTransaction_returnType_ok() {
        String line = "r,banana,20";
        FruitTransaction actual = new FruitTransaction(FruitTransaction.Operation.RETURN,
                BANANA, 20);
        FruitTransaction expected = csvParserUtil.extractTransaction(line);
        assertEquals(expected, actual);
    }

    @Test
    public void extractTransaction_supplyType_ok() {
        String line = "s,banana,20";
        FruitTransaction actual = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                BANANA, 20);
        FruitTransaction expected = csvParserUtil.extractTransaction(line);
        assertEquals(expected, actual);
    }

    @Test
    public void extractTransaction_lineHasWrongFormat_notOk() {
        String line = "wrong format";
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage(INVALID_LINE_FORMAT);
        csvParserUtil.extractTransaction(line);
    }

    @Test
    public void extractTransaction_lineHasWrongQuantity_notOk() {
        String line = "s,banana,number";
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage(INVALID_LINE_FORMAT);
        csvParserUtil.extractTransaction(line);
    }

    @Test
    public void extractTransaction_lineHasWrongOperation_notOk() {
        String line = "a,banana,20";
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage(INVALID_LINE_FORMAT);
        csvParserUtil.extractTransaction(line);
    }

    @After
    public void tearDown() {
    }
}
