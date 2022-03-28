package service;

import model.FruitTransaction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitParserImplTest {
    private static FruitParser fruitParser;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitParser = new FruitParserImpl();
        fruitTransaction = new FruitTransaction();
    }

    @Test
    public void validParsedData_OK() {
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        String actualData = " b,banana,20";
        FruitTransaction actual = fruitParser.getFromCsvRow(actualData);
        Assert.assertEquals(fruitTransaction, actual);
    }
}
