package core.basesyntax.impl;

import core.basesyntax.FruitTransaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TransactionParseServiceImplTest {
    private static final String VALID_DATA = "b,banana,10";
    private static final String INVALID_DATA = "b,banana,-50";
    private TransactionParseServiceImpl parseService;

    @Before
    public void setUp() {
        parseService = new TransactionParseServiceImpl();
    }

    @Test
    public void parse_ValidData_Ok() {
        FruitTransaction expected = new FruitTransaction();
        expected.setOperation(FruitTransaction.Operation.BALANCE);
        expected.typeFruit("banana");
        expected.typeQuantity(10);
        Object actual = parseService.parse(VALID_DATA);
        Assert.assertEquals(actual,expected);
    }

    @Test (expected = RuntimeException.class)
    public void parse_InvalidData_NotOk() {
        parseService.parse(INVALID_DATA);
    }
}
