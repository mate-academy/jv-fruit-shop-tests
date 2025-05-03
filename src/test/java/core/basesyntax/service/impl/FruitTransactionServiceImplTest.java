package core.basesyntax.service.impl;

import static core.basesyntax.model.FruitTransaction.Operation;
import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FruitTransactionServiceImplTest {
    private static final FruitTransactionService fruitTransactionService =
            new FruitTransactionServiceImpl();
    private static final List<FruitTransaction> expectedFruitTransactions = List.of(
            new FruitTransaction(Operation.BALANCE, new Fruit("banana", 20)),
            new FruitTransaction(Operation.BALANCE, new Fruit("apple", 100)),
            new FruitTransaction(Operation.SUPPLY, new Fruit("banana", 100)),
            new FruitTransaction(Operation.PURCHASE, new Fruit("banana", 13)),
            new FruitTransaction(Operation.RETURN, new Fruit("apple", 10)),
            new FruitTransaction(Operation.PURCHASE, new Fruit("apple", 20)),
            new FruitTransaction(Operation.PURCHASE, new Fruit("banana", 5)),
            new FruitTransaction(Operation.SUPPLY, new Fruit("banana", 50)),
            new FruitTransaction(Operation.SUPPLY, new Fruit("orange", 100)),
            new FruitTransaction(Operation.RETURN, new Fruit("melon", 20)));
    private static final String EMPTY_STRING = "";
    private static List<String> inputTransactions;
    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setUp() {
        inputTransactions = new ArrayList<>(List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50",
                "s,orange,100",
                "r,melon,20"));
    }

    @After
    public void tearDown() {
        inputTransactions.clear();
    }

    @Test
    public void getFruitTransactions_nullTransactions_NotOk() {
        exceptionRule.expect(NullPointerException.class);
        exceptionRule.expectMessage("Transactions cannot be null");
        fruitTransactionService.getFruitTransactions(null);
    }

    @Test
    public void getFruitTransactions_nullTransaction_NotOk() {
        exceptionRule.expect(NullPointerException.class);
        exceptionRule.expectMessage("Transaction cannot be null");
        inputTransactions.add(null);
        fruitTransactionService.getFruitTransactions(inputTransactions);
    }

    @Test
    public void getFruitTransactions_validTransactions_Ok() {
        List<FruitTransaction> actualResult =
                fruitTransactionService.getFruitTransactions(inputTransactions);
        assertEquals(expectedFruitTransactions, actualResult);
    }

    @Test
    public void getFruitTransactions_emptyTransactions_Ok() {
        List<FruitTransaction> actualResult =
                fruitTransactionService.getFruitTransactions(Collections.emptyList());
        assertEquals(Collections.emptyList(), actualResult);
    }

    @Test
    public void getFruitTransactions_emptyTransactionsWithHeader_Ok() {
        String header = "type,fruit,quantity";
        List<FruitTransaction> actualResult =
                fruitTransactionService.getFruitTransactions(List.of(header));
        assertEquals(Collections.emptyList(), actualResult);
    }

    @Test
    public void getFruitTransactions_emptyRowsInTransactions_Ok() {
        inputTransactions.add(0, EMPTY_STRING);
        inputTransactions.add(2, EMPTY_STRING);
        inputTransactions.add(EMPTY_STRING);
        List<FruitTransaction> actualResult =
                fruitTransactionService.getFruitTransactions(inputTransactions);
        assertEquals(expectedFruitTransactions, actualResult);
    }

    @Test
    public void getFruitTransactions_emptyAllRowsInTransactions_Ok() {
        inputTransactions.clear();
        inputTransactions.add(EMPTY_STRING);
        inputTransactions.add(EMPTY_STRING);
        inputTransactions.add(EMPTY_STRING);
        List<FruitTransaction> actualResult =
                fruitTransactionService.getFruitTransactions(inputTransactions);
        assertEquals(Collections.emptyList(), actualResult);
    }

    @Test
    public void getFruitTransactions_invalidFruitOperation_NotOk() {
        inputTransactions.add("x,orange,100");
        String lastTransaction = inputTransactions.get(inputTransactions.size() - 1);
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("No such fruit operation type: " + lastTransaction.charAt(0));
        fruitTransactionService.getFruitTransactions(inputTransactions);
    }

    @Test
    public void getFruitTransactions_emptyFruitOperation_NotOk() {
        inputTransactions.add(",apple,100");
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Fruit operation type cannot be empty");
        fruitTransactionService.getFruitTransactions(inputTransactions);
    }

    @Test
    public void getFruitTransactions_emptyFruitName_NotOk() {
        inputTransactions.add("s,,100");
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Fruit name cannot be empty");
        fruitTransactionService.getFruitTransactions(inputTransactions);
    }

    @Test
    public void getFruitTransactions_emptyFruitQuantity_NotOk() {
        inputTransactions.add("s,apple,");
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Fruit quantity cannot be empty");
        fruitTransactionService.getFruitTransactions(inputTransactions);
    }

    @Test
    public void getFruitTransactions_emptyAllFruitFields_NotOk() {
        inputTransactions.add(",,");
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Fruit quantity cannot be empty");
        fruitTransactionService.getFruitTransactions(inputTransactions);
    }
}
