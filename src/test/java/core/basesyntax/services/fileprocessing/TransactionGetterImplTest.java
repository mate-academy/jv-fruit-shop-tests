package core.basesyntax.services.fileprocessing;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.exceptions.OperationException;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.services.fileprocessing.impl.TransactionGetterImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TransactionGetterImplTest {
    private static TransactionGetter transactionGetter;
    private static final int OPERATION_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final String EXPECTED_EXCEPTION_MESSAGE = "Can't parse integer for argument";
    private static final List<String[]> NORMAL_TEST_DATA =
            List.of(new String[] {"b", "banana", "20"},
                    new String[] {"b", "apple", "100"},
                    new String[] {"s", "banana", "100"},
                    new String[] {"p", "banana", "13"},
                    new String[] {"r", "banana", "10"},
                    new String[] {"s", "apple", "20"},
                    new String[] {"p", "apple", "5"},
                    new String[] {"r", "apple", "5"});

    private static final List<String[]> ANOTHER_NORMAL_TEST_DATA =
            List.of(new String[] {"b", "banana", "100"},
                    new String[] {"b", "apple", "20"},
                    new String[] {"s", "banana", "13"},
                    new String[] {"p", "banana", "100"},
                    new String[] {"r", "apple", "20"},
                    new String[] {"s", "apple", "10"},
                    new String[] {"p", "banana", "5"},
                    new String[] {"r", "apple", "5"});
    private static final List<String[]> BAD_OPERATIONS =
            List.of(new String[] {"b", "banana", "20"},
                    new String[] {"q", "apple", "100"},
                    new String[] {"w", "banana", "100"},
                    new String[] {"e", "banana", "13"},
                    new String[] {"r", "banana", "10"},
                    new String[] {"t", "apple", "20"},
                    new String[] {"y", "apple", "5"});

    private static final List<String[]> BAD_INTEGERS =
            List.of(new String[] {"b", "banana", "twenty"},
                    new String[] {"b", "apple", "thirty"},
                    new String[] {"s", "banana", "forty"},
                    new String[] {"p", "banana", "fifty"},
                    new String[] {"r", "banana", "sixty"},
                    new String[] {"s", "apple", "seventy"},
                    new String[] {"p", "apple", "eighty"},
                    new String[] {"r", "apple", "ninety"});

    @BeforeAll
    static void initTransactionGetter() {
        transactionGetter = new TransactionGetterImpl();
    }

    @Test
    void getTransactionsData_putBadDataWithNumbersInWords_notOk() {
        RuntimeException numberFormatException = assertThrows(RuntimeException.class,
                () -> transactionGetter.getTransactionsData(BAD_INTEGERS));
        assertTrue(numberFormatException.getMessage().startsWith(EXPECTED_EXCEPTION_MESSAGE));
    }

    @Test
    void getTransactionsData_putBadDataWithNonExistingOperations_notOk() {
        assertThrows(OperationException.class,
                () -> transactionGetter.getTransactionsData(BAD_OPERATIONS));
    }

    @Test
    void getTransactionsData_putNormalButDifferentData() {
        List<FruitTransaction> normalFruitTransactions =
                fruitTransactionsTestSetter(NORMAL_TEST_DATA);
        List<FruitTransaction> anotherNormalFruitTransactions =
                fruitTransactionsTestSetter(ANOTHER_NORMAL_TEST_DATA);
        assertFalse(assertListEquals(normalFruitTransactions, anotherNormalFruitTransactions));
        assertFalse(assertListEquals(normalFruitTransactions,
                transactionGetter.getTransactionsData(ANOTHER_NORMAL_TEST_DATA)));
        assertFalse(assertListEquals(transactionGetter.getTransactionsData(NORMAL_TEST_DATA),
                transactionGetter.getTransactionsData(ANOTHER_NORMAL_TEST_DATA)));
    }

    @Test
    void getTransactionsData_putNormalData_Ok() {
        List<FruitTransaction> normalFruitTransactions =
                fruitTransactionsTestSetter(NORMAL_TEST_DATA);
        assertTrue(assertListEquals(normalFruitTransactions,
                transactionGetter.getTransactionsData(NORMAL_TEST_DATA)));
    }

    private List<FruitTransaction> fruitTransactionsTestSetter(List<String[]> testData) {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        for (String[] strings : testData) {
            FruitTransaction fruitTransaction = new FruitTransaction();
            fruitTransaction.setOperation(
                    FruitTransaction.Operation.getOperationFromCode(strings[OPERATION_INDEX]));
            fruitTransaction.setFruit(strings[FRUIT_INDEX]);
            fruitTransaction.setQuantity(Integer.parseInt(strings[QUANTITY_INDEX]));
            fruitTransactions.add(fruitTransaction);
        }
        return fruitTransactions;
    }

    private boolean assertListEquals(List<FruitTransaction> expectedResult,
                                     List<FruitTransaction> actualResult) {
        for (int i = 0; i < expectedResult.size(); i++) {
            if (!expectedResult.get(i).getOperation().equals(actualResult.get(i).getOperation())
                    || !expectedResult.get(i).getFruit().equals(actualResult.get(i).getFruit())
                    || expectedResult.get(i).getQuantity() != actualResult.get(i).getQuantity()) {
                return false;
            }
        }
        return true;
    }
}
