package core.basesyntax;

import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.FruitTransaction;
import org.junit.Assert;
import org.junit.Test;
import service.FruitTransactionServiceImpl;
import util.ValidationDataException;

public class FruitTransactionServiceTest {
    private static final FruitTransactionServiceImpl fruitTransactionService =
            new FruitTransactionServiceImpl();
    private static final List<String> transactionsWithWrongTransaction =
            new ArrayList<>(Arrays.asList("r,apple,10", "p,apple,20, 5555", "b,banana,20"));
    private static final List<String> correctTransactions =
            new ArrayList<>(Arrays.asList("s,banana,100", "p,banana,5", "r,apple,10"));
    private static final List<String> transactionsWithWrongCommandType =
            new ArrayList<>(Arrays.asList("s,banana,100", "o,banana,5", "r,apple,10"));
    private static final List<String> transactionsWithWrongQuantityType =
            new ArrayList<>(Arrays.asList("s,banana,dddd", "p,banana,5", "r,apple,10"));
    private static final FruitTransaction transaction1 =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100);
    private static final FruitTransaction transaction2 =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5);
    private static final FruitTransaction transaction3 =
            new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10);
    private static final List<FruitTransaction> correctTransactionsList =
            new ArrayList<>(Arrays.asList(transaction1, transaction2, transaction3));

    @Test
    public void testCorrectTransactions() {
        List<FruitTransaction> fruitTransactions =
                fruitTransactionService.parseTransactions(correctTransactions);
        Assert.assertEquals(fruitTransactions, correctTransactionsList);
    }

    @Test
    public void testWrongInputFormat() {
        ValidationDataException exception = assertThrows(ValidationDataException.class, () ->
                fruitTransactionService.parseTransactions(transactionsWithWrongTransaction));
        Assert.assertEquals(exception.getMessage(), "Invalid input format in file");
    }

    @Test
    public void testWrongCommandType() {
        ValidationDataException exception = assertThrows(ValidationDataException.class, () ->
                fruitTransactionService.parseTransactions(transactionsWithWrongCommandType));
        Assert.assertEquals(exception.getMessage(), "Invalid operation code in file");
    }

    @Test
    public void testWrongQuantity() {
        ValidationDataException exception = assertThrows(ValidationDataException.class, () ->
                fruitTransactionService.parseTransactions(transactionsWithWrongQuantityType));
        Assert.assertEquals(exception.getMessage(), "Invalid quantity format in file");
    }
}
