package core.basesyntax.services;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionService;
import core.basesyntax.service.impl.FruitTransactionServiceImpl;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static FruitTransactionService fruitTransactionService;

    private static final List<String> VALID_TRANSACTIONS = List.of(
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50"
    );

    private static final List<FruitTransaction> VALID_TRANSACTIONS_FRUIT_OBJECTS = List.of(
            new FruitTransaction(FruitTransaction.Operation.BALANCE, BANANA, 20),
            new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, 100),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA, 100),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA, 13),
            new FruitTransaction(FruitTransaction.Operation.RETURN, APPLE, 10),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE,APPLE, 20),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA, 5),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA, 50)
    );
    private static final List<String> INVALID_TRANSACTIONS = List.of(
            "b,banana,20",
            "b,apple,100",
            "x,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50"
    );
    private static final List<String> NEGATIVE_TRANSACTIONS = List.of(
            "b,banana,-20",
            "b,apple,-100",
            "x,banana,-100",
            "p,banana,-13",
            "r,apple,-10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50"
    );

    @BeforeClass
    public static void beforeClass() {
        fruitTransactionService = new FruitTransactionServiceImpl();
    }

    @Test
    public void createFruitTransaction_validData_Ok() {
        List<FruitTransaction> actual
                = fruitTransactionService.createFruitTransactions(VALID_TRANSACTIONS);
        Assert.assertEquals(VALID_TRANSACTIONS_FRUIT_OBJECTS, actual);
    }

    @Test(expected = NoSuchElementException.class)
    public void createFruitTransaction_invalidData_notOk() {
        fruitTransactionService.createFruitTransactions(INVALID_TRANSACTIONS);
    }

    @Test(expected = NoSuchElementException.class)
    public void createFruitTransaction_negativeAmountData_notOk() {
        fruitTransactionService.createFruitTransactions(NEGATIVE_TRANSACTIONS);
    }

    @Test(expected = RuntimeException.class)
    public void createFruitTransaction_null_notOk() {
        fruitTransactionService.createFruitTransactions(null);
    }
}
