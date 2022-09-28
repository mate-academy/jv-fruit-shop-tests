package core.basesyntax;

import core.basesyntax.impl.TransactionParseServiceImpl;
import core.basesyntax.service.ParseService;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


public class TransactionParseServiceImplTest {
    private static final String VALID_DATA = "b,banana,10";
    private static final String INVALID_DATA = "b,banana,-50";
    private static ParseService<FruitTransaction> parseService;

    @BeforeClass
    public static void beforeClass() {
        parseService = new TransactionParseServiceImpl();
    }

    /*
    @Test
    public void parse_ParseValidData_Ok() {
        FruitTransaction expected = new FruitTransaction();
        expected.setOperation(FruitTransaction.Operation.BALANCE);
        expected.typeFruit("banana");
        expected.typeQuantity(10);
        FruitTransaction actual = parseService.parse(VALID_DATA);
        Assert.assertEquals(actual,expected);
        //Assert.assertEquals(actual,expected);
    }
    */

    @Test (expected = RuntimeException.class)
    public void parse_ParseInvalidData_NoyOk() {
        parseService.parse(INVALID_DATA);
    }
}
