package core.basesyntax;

import core.basesyntax.model.TransactionDto;
import org.junit.Assert;
import org.junit.Test;

public class TransactionDtoTest {
    private static String expected;
    private static TransactionDto transactionDto;
    private static String actual;

    @Test
    public void toString_TransactionDto_ok() {
        transactionDto = new TransactionDto("b", "banana", 10);
        expected = "TransactionDto{"
                + "operation='" + transactionDto.getOperation() + '\''
                + ", fruitName='" + transactionDto.getFruitName() + '\''
                + ", quantity=" + transactionDto.getQuantity()
                + '}';
        actual = transactionDto.toString();
        Assert.assertEquals(expected, actual);
    }
}
