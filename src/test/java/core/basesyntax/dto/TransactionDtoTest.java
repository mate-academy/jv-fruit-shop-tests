package core.basesyntax.dto;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionDtoTest {
    private static final String APPLE = "apple";
    private static final Integer AMOUNT = 100;
    private static TransactionDto transactionDto;

    @BeforeClass
    public static void beforeClass() {
        transactionDto = new TransactionDto(Operation.BALANCE, new Fruit(APPLE), AMOUNT);
    }

    @Test
    public void getOperationTest_Ok() {
        Operation expected = Operation.BALANCE;
        Operation actual = transactionDto.getOperation();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getFruitTest_Ok() {
        Fruit expected = new Fruit(APPLE);
        Fruit actual = transactionDto.getFruit();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAmountTest_Ok() {
        Integer actual = transactionDto.getAmount();
        Assert.assertEquals(AMOUNT, actual);
    }
}
