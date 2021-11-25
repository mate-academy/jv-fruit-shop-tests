package core.basesyntax;

import core.basesyntax.model.TransactionDto;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionDtoTest {
    private static TransactionDto transactionDto;
    private static String expected;

    @BeforeClass
    public static void beforeClass() {
        transactionDto = new TransactionDto("b", "banana", 10);
    }

    @Test
    public void toStringTransactionDto_ok() {
        expected = "TransactionDto{"
                + "operation='" + transactionDto.getOperation() + '\''
                + ", fruitName='" + transactionDto.getFruitName() + '\''
                + ", quantity=" + transactionDto.getQuantity()
                + '}';
        Assert.assertEquals(expected, transactionDto.toString());
    }

    @Test
    public void getOperation_ok() {
        Assert.assertEquals("b", transactionDto.getOperation());
    }
}
