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
    private static final List<String> TRANSACTIONS_WITH_ONE_WRONG =
            new ArrayList<>(Arrays.asList("r,apple,10", "p,apple,20, 5555", "b,banana,20"));
    private static final List<String> CORRECT_TRANSACTIONS =
            new ArrayList<>(Arrays.asList("s,banana,100", "p,banana,5", "r,apple,10"));
    private static final List<String> TRANSACTIONS_WITH_WRONG_COMMAND_TYPE =
            new ArrayList<>(Arrays.asList("s,banana,100", "o,banana,5", "r,apple,10"));
    private static final List<String> TRANSACTIONS_WITH_WRONG_QUANTITY_TYPE =
            new ArrayList<>(Arrays.asList("s,banana,dddd", "p,banana,5", "r,apple,10"));
    private static final FruitTransaction FIRST_TRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100);
    private static final FruitTransaction SECOND_TRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5);
    private static final FruitTransaction THIRD_TRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10);
    private static final List<FruitTransaction> CORRECT_TRANSACTION_LIST =
            new ArrayList<>(
                    Arrays.asList(FIRST_TRANSACTION, SECOND_TRANSACTION, THIRD_TRANSACTION));
    private FruitTransactionServiceImpl fruitTransactionService =
            new FruitTransactionServiceImpl();

    @Test
    public void parseCorrectTransactionsAndGetOk() {
        List<FruitTransaction> fruitTransactions =
                fruitTransactionService.parseTransactions(CORRECT_TRANSACTIONS);
        Assert.assertEquals(fruitTransactions, CORRECT_TRANSACTION_LIST);
    }

    @Test
    public void tryParseIncorrectTransactionsAndThrowException() {
        ValidationDataException exception = assertThrows(ValidationDataException.class, () ->
                fruitTransactionService.parseTransactions(TRANSACTIONS_WITH_ONE_WRONG));
        Assert.assertEquals(exception.getMessage(), "Invalid input format in file");
    }

    @Test
    public void tryParseWrongCommandTypeAndThrowException() {
        ValidationDataException exception = assertThrows(ValidationDataException.class, () ->
                fruitTransactionService.parseTransactions(TRANSACTIONS_WITH_WRONG_COMMAND_TYPE));
        Assert.assertEquals(exception.getMessage(), "Invalid operation code in file");
    }

    @Test
    public void tryParseWrongQuantityTypeAndThrowException() {
        ValidationDataException exception = assertThrows(ValidationDataException.class, () ->
                fruitTransactionService.parseTransactions(TRANSACTIONS_WITH_WRONG_QUANTITY_TYPE));
        Assert.assertEquals(exception.getMessage(), "Invalid quantity format in file");
    }
}
