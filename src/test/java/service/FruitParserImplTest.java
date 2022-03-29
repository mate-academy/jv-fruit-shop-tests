package service;

import model.FruitTransaction;
import org.junit.Assert;
import org.junit.Test;

public class FruitParserImplTest {
    @Test
    public void validParsedData_Ok() {
        FruitParser fruitParser;
        FruitTransaction fruitTransaction;
        fruitParser = new FruitParserImpl();
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        String actualData = " b,banana,20";
        FruitTransaction actual = fruitParser.parseFruitTransaction(actualData);
        Assert.assertEquals(fruitTransaction, actual);
    }
}
