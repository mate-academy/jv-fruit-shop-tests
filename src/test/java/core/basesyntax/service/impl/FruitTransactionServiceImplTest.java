package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionService;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FruitTransactionServiceImplTest extends FruitTransaction {
    private FruitTransactionService transactionService;
    private final List<String> list = List.of("type,fruit,quantity", "b,banana,50", "b,apple,30",
            "p,apple,20", "s,banana,10", "p,banana,24", "r,banana,50");
    private final List<FruitTransaction> actual = List.of(
            new FruitTransaction(Operation.BALANCE, "banana", 50),
            new FruitTransaction(Operation.BALANCE, "apple", 30),
            new FruitTransaction(Operation.PURCHASE, "apple", 20),
            new FruitTransaction(Operation.SUPPLY, "banana", 10),
            new FruitTransaction(Operation.PURCHASE, "banana", 24),
            new FruitTransaction(Operation.RETURN, "banana", 50)
    );

    @Before
    public void setUp() {
        transactionService = new FruitTransactionServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void parseFruitTransactions_NullData_NotOK() {
        transactionService.parseFruitTransactions(null);
    }

    @Test
    public void parseFruitTransactions_ValidData_OK() {
        List<FruitTransaction> expected = transactionService.parseFruitTransactions(list);
        assertEquals(expected, actual);
    }
}
