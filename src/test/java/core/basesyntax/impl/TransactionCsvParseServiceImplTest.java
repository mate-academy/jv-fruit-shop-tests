package core.basesyntax.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionCsvParseServiceImplTest {
    private static final String VALID_DATA = "b,banana,50";
    private static final String INVALID_DATA = "b,banana,-50";
    private static ParseService<FruitTransaction> parseService;

    @BeforeClass
    public static void beforeClass() {
        parseService = new TransactionCsvParseServiceImpl();
    }

    @Test
    public void parse_ParseValidData_Ok() {
        FruitTransaction expected = new FruitTransaction();
        expected.setOperation(FruitTransaction.Operation.BALANCE);
        expected.setFruit("banana");
        expected.setQuantity(50);
        FruitTransaction actual = parseService.parse(VALID_DATA);
        Assert.assertEquals(expected,actual);
    }

    @Test (expected = RuntimeException.class)
    public void parse_ParseInvalidData_NoyOk() {
        parseService.parse(INVALID_DATA);
    }
}
