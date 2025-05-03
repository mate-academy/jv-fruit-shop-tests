package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionServiceImplTest {
    private static final int TEST_FRUIT_AMOUNT_ONE = 10;
    private static final int TEST_FRUIT_AMOUNT_TWO = 20;
    private static final String TEST_FRUIT_TYPE_ONE = "avocado";
    private static final String TEST_FRUIT_TYPE_TWO = "papaya";
    private static final String TEST_STRING_ONE = "test,file";
    private static final String TEST_STRING_TWO = "b,avocado,10";
    private static final String TEST_STRING_THREE = "s,papaya,20";
    private static final String TEST_STRING_FOUR_NEGATIVE_AMOUNT = "b,avocado,-10";
    private static final String EMPTY_STRING = "";
    private static final FruitTransaction.Operation BALANCE
            = FruitTransaction.Operation.BALANCE;
    private static final FruitTransaction.Operation SUPPLY
            = FruitTransaction.Operation.SUPPLY;
    private static List<String> testStringsList;
    private static List<String> testStringsListEmpty;
    private static List<FruitTransaction> expectedList;
    private static TransactionService transactionService;

    @BeforeClass
    public static void beforeClass() {
        transactionService = new TransactionServiceImpl();
        testStringsList = new ArrayList<>();
        testStringsListEmpty = new ArrayList<>();
        testStringsList.add(TEST_STRING_ONE);
        testStringsList.add(TEST_STRING_TWO);
        testStringsList.add(TEST_STRING_THREE);
        FruitTransaction fruitTransactionOne = new FruitTransaction();
        fruitTransactionOne.setFruitType(TEST_FRUIT_TYPE_ONE);
        fruitTransactionOne.setAmount(TEST_FRUIT_AMOUNT_ONE);
        fruitTransactionOne.setOperation(BALANCE);
        FruitTransaction fruitTransactionTwo = new FruitTransaction();
        fruitTransactionTwo.setFruitType(TEST_FRUIT_TYPE_TWO);
        fruitTransactionTwo.setAmount(TEST_FRUIT_AMOUNT_TWO);
        fruitTransactionTwo.setOperation(SUPPLY);
        expectedList = new ArrayList<>();
        expectedList.add(fruitTransactionOne);
        expectedList.add(fruitTransactionTwo);
    }

    @Test(expected = RuntimeException.class)
    public void processData_listNull_notOk() {
        transactionService.processData(null);
    }

    @Test(expected = RuntimeException.class)
    public void processData_listEmpty_notOk() {
        transactionService.processData(testStringsListEmpty);
    }

    @Test(expected = RuntimeException.class)
    public void processData_stringEmpty_notOk() {
        testStringsList.add(EMPTY_STRING);
        transactionService.processData(testStringsList);
    }

    @Test(expected = RuntimeException.class)
    public void processData_negativeAmount_notOk() {
        testStringsList.add(TEST_STRING_FOUR_NEGATIVE_AMOUNT);
        transactionService.processData(testStringsList);
    }

    @Test
    public void processData_listValid_ok() {
        testStringsList.remove(EMPTY_STRING);
        testStringsList.remove(TEST_STRING_FOUR_NEGATIVE_AMOUNT);
        List<FruitTransaction> actualList = transactionService.processData(testStringsList);
        assertEquals(expectedList, actualList);
    }
}
