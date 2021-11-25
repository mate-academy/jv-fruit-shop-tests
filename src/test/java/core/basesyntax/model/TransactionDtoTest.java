/*
package core.basesyntax.model;

import core.basesyntax.dao.FruitDaoImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class TransactionDtoTest {
    private static TransactionDto transactionDto;

    @BeforeClass
    public static void beforeAll() {
        transactionDto = new TransactionDto(Operation.SUPPLY, "banana", 100);
    }

    @Test
    public void getOperation_ok(){
        Operation expected = Operation.SUPPLY;
        Operation actual = transactionDto.getOperation();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getFruitName_ok(){
        String expected = "banana";
        String actual = transactionDto.getFruitName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getQuantity_ok(){
        int expected = 100;
        int actual = transactionDto.getQuantity();
        Assert.assertEquals(expected, actual);
    }



}
*/
