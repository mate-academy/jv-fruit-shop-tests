package service;

import model.FruitTransaction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitParserImplTest {
    private static FruitParser fruitParser;

    @BeforeClass
    public static void beforeClass() {
        fruitParser = new FruitParserImpl();
    }

    @Test
    public void parsedData_Ok() {
        FruitTransaction expected = new FruitTransaction();
        expected.setOperation(FruitTransaction.Operation.BALANCE);
        expected.setFruit("banana");
        expected.setQuantity(20);
        String actualData = " b,banana,20";
        FruitTransaction actual = fruitParser.parseFruitTransaction(actualData);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void nullFruitAndQuantity_NotOk() {
        String actualData = " b, , ";
        fruitParser.parseFruitTransaction(actualData);
    }

    @Test(expected = RuntimeException.class)
    public void nullFruitData_NotOk() {
        String actualData = " b, ,20";
        fruitParser.parseFruitTransaction(actualData);
    }

    @Test(expected = RuntimeException.class)
    public void nullTypeAndFruit_NotOk() {
        String actualData = " , ,20";
        fruitParser.parseFruitTransaction(actualData);
    }

    @Test(expected = RuntimeException.class)
    public void nullType_NotOk() {
        String actualData = " ,banana,20";
        fruitParser.parseFruitTransaction(actualData);
    }

    @Test(expected = RuntimeException.class)
    public void nullQuantityData_NotOk() {
        String actualData = " b,banana, ";
        fruitParser.parseFruitTransaction(actualData);
    }

    @Test(expected = RuntimeException.class)
    public void nullData_NotOk() {
        String actualData = " , , ";
        fruitParser.parseFruitTransaction(actualData);
    }

    @Test (expected = RuntimeException.class)
    public void parse_nullData_NotOk() {
        fruitParser.parseFruitTransaction(null);
    }
}
