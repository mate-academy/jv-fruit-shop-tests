package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class TransactionServiceImplTest {
    private static final List<String> NULL_DATA = null;
    private static final List<String> WRONG_DATA_FORMAT =
            List.of("wrong", "b", "listFormat");
    private static final List<String> WRONG_OPERATION_FORMAT =
            List.of("type,fruit,quantity", "i,banana,25");
    private static final List<String> RIGHT_OPERATION_FORMAT =
            List.of("type,fruit,quantity", "b,banana,25");
    private static final FruitTransaction.Operation CORRECT_OPERATION =
            FruitTransaction.Operation.BALANCE;
    private static final String CORRECT_FRUIT = "banana";
    private static final int CORRECT_QUANTITY = 25;
    private final TransactionServiceImpl transactionService = new TransactionServiceImpl();

    @Test
    void createTransactionList_NullData_NotOk() {
        Assert.assertThrows(RuntimeException.class,
                () -> transactionService.createTransactionsList(NULL_DATA));
    }

    @Test
    void createTransactionList_IncorrectDataFormat_NotOk() {
        Assert.assertThrows(RuntimeException.class,
                () -> transactionService.createTransactionsList(WRONG_DATA_FORMAT));
    }

    @Test
    void createTransactionList_IncorrectOperationFormat_NotOk() {
        Assert.assertThrows(RuntimeException.class,
                () -> transactionService.createTransactionsList(WRONG_OPERATION_FORMAT));
    }

    @Test
    void createTransactionList_CorrectList_Ok() {
        List<FruitTransaction> transactions =
                transactionService.createTransactionsList(RIGHT_OPERATION_FORMAT);
        FruitTransaction expected = new FruitTransaction();
        expected.setOperation(CORRECT_OPERATION);
        expected.setFruit(CORRECT_FRUIT);
        expected.setQuantity(CORRECT_QUANTITY);
        FruitTransaction actual = transactions.get(0);
        Assert.assertEquals(expected, actual);
    }
}
