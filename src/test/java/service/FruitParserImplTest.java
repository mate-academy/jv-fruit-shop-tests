package service;

import model.FruitTransaction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitParserImplTest {
    private static FruitParser fruitParser;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitParser = new FruitParserImpl();
    }

    @Test
    public void parsedData_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        String actualData = " b,banana,20";
        FruitTransaction actual = fruitParser.parseFruitTransaction(actualData);
        Assert.assertEquals(fruitTransaction, actual);
    }

    @Test(expected = NullPointerException.class)
    public void nullOperationData_NotOk() {
        String actualData = " ,banana,20";
        fruitParser.parseFruitTransaction(actualData);
    }

    @Test(expected = NullPointerException.class)
    public void nullFruitData_NotOk() {
        String actualData = " b,,20";
        fruitParser.parseFruitTransaction(actualData);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void nullQuantityData_NotOk() {
        String actualData = " b,banana, ";
        fruitParser.parseFruitTransaction(actualData);
    }
}
