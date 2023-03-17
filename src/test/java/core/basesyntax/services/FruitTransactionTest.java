package core.basesyntax.services;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionService;
import core.basesyntax.service.impl.FruitTransactionServiceImpl;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class FruitTransactionTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
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
    private FruitTransactionService fruitTransactionService = new FruitTransactionServiceImpl();

    @Test
    public void createFruitTransaction_validData_Ok() {
        List<FruitTransaction> actual
                = fruitTransactionService.createFruitTransactions(VALID_TRANSACTIONS);
        Assert.assertEquals(VALID_TRANSACTIONS_FRUIT_OBJECTS, actual);
    }
}
