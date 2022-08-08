package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionsServiceImplTest {
    private static FruitTransactionsService fruitTransactionsService;
    private static List<FruitTransaction> fruitTransactions;
    private static Fruit banana;
    private static Fruit apple;
    private static Fruit lemon;

    @BeforeClass
    public static void beforeClass() {
        fruitTransactionsService = new FruitTransactionsServiceImpl();
        initializeFruitTransactions();
    }

    @Test
    public void getFruitTransactions_defaultCase_Ok() {
        List<FruitTransaction> expected = fruitTransactions;
        List<FruitTransaction> actual = fruitTransactionsService
                .getFruitTransactions(Util.INPUT_FILE_LINES);
        assertEquals("Expected should be equal to "
                + actual + " but was: "
                + expected, expected, actual);
    }

    private static void initializeFruitTransactions() {
        initializeFruits();
        fruitTransactions = new ArrayList<>(List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, banana, 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, apple, 10),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, apple, 15),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, apple, 5),
                new FruitTransaction(FruitTransaction.Operation.RETURN, lemon, 50),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, lemon, 20)));
    }

    private static void initializeFruits() {
        banana = new Fruit("banana");
        apple = new Fruit("apple");
        lemon = new Fruit("lemon");
    }
}
