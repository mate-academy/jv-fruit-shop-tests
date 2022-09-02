package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.impl.FruitTransactionServiceImpl;
import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionServiceTest {
    private static FruitTransactionService fruitTransactionService;
    private static List<String> listToParse;

    @BeforeClass
    public static void beforeClass() {
        fruitTransactionService = new FruitTransactionServiceImpl();
    }

    @Before
    public void setUp() {
        listToParse = new ArrayList<>();
        listToParse.add("type,fruit,quantity");
        listToParse.add("b,banana,10");
        listToParse.add("s,apple,20");
        listToParse.add("p,apple,5");
        listToParse.add("r,banana,10");
    }

    @Test
    public void parseFruitTransactions_allExistingOperations_Ok() {
        FruitTransaction firstFruitTransaction = new FruitTransaction();
        firstFruitTransaction.setFruitName("banana");
        firstFruitTransaction.setQuantity(10);
        firstFruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        FruitTransaction secondFruitTransaction = new FruitTransaction();
        secondFruitTransaction.setFruitName("apple");
        secondFruitTransaction.setQuantity(20);
        secondFruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        FruitTransaction thirdFruitTransaction = new FruitTransaction();
        thirdFruitTransaction.setFruitName("apple");
        thirdFruitTransaction.setQuantity(5);
        thirdFruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        FruitTransaction fourthFruitTransaction = new FruitTransaction();
        fourthFruitTransaction.setFruitName("banana");
        fourthFruitTransaction.setQuantity(10);
        fourthFruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(firstFruitTransaction);
        expected.add(secondFruitTransaction);
        expected.add(thirdFruitTransaction);
        expected.add(fourthFruitTransaction);
        List<FruitTransaction> actual = fruitTransactionService.parseFruitTransactions(listToParse);
        assertEquals(expected,actual);
    }

    @Test (expected = RuntimeException.class)
    public void parseFruitTransactions_nonExistingOperation_throwsException() {
        listToParse = Collections.emptyList();
        listToParse.add("w,apple,5");
        fruitTransactionService.parseFruitTransactions(listToParse);
    }
}
