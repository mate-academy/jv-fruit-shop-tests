package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionsServiceImplTest {
    private static FruitTransactionsService fruitTransactionsService;
    private static List<FruitTransaction> fruitTransactions;
    private static List<String> emptyList;

    @BeforeClass
    public static void beforeClass() {
        fruitTransactionsService = new FruitTransactionsServiceImpl();
        emptyList = new ArrayList<>();
    }

    @Before
    public void init() {
        initializeFruitTransactions();
    }

    @Test
    public void getFruitTransactions_defaultCase_Ok() {
        List<FruitTransaction> expected = fruitTransactions;
        List<FruitTransaction> actual = fruitTransactionsService
                .getFruitTransactions(Util.INPUT_FILE_LINES);
        assertEquals("getFruitTransactions should return List of FruitTransactions: "
                + expected + " but was: "
                + actual, expected, actual);
    }

    @Test
    public void getFruitTransactions_emptyList_Ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        List<FruitTransaction> actual = fruitTransactionsService.getFruitTransactions(emptyList);
        assertEquals("getFruitTransactions should return empty List of FruitTransactions: "
                + expected + " but was: "
                + actual, expected, actual);
    }

    private static void initializeFruitTransactions() {
        fruitTransactions = new ArrayList<>(List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, Util.banana, 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, Util.apple, 10),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, Util.apple, 15),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, Util.apple, 5),
                new FruitTransaction(FruitTransaction.Operation.RETURN, Util.lemon, 50),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, Util.lemon, 20)));
    }
}
