package core.basesyntax.service;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.ReportCsvParserImpl;
import org.junit.After;
import core.basesyntax.model.FruitTransaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ReportCsvParserImplTest {
    private static final Fruit BANANA = new Fruit("banana");
    private static final Fruit MANGO = new Fruit("mango");
    private static final Fruit APPLE = new Fruit("apple");
    private final Fruit ORANGE = new Fruit("orange");
    private static ReportCsvParser parser;
    private List<String> transactions;

    @BeforeClass
    public static void beforeClass() {
        parser = new ReportCsvParserImpl();
    }

    @Before
    public void setUp() {
        transactions = new ArrayList<>();
        transactions.add("type,fruit,quantity");
        transactions.add("b,banana,25");
        transactions.add("s,mango,35");
        transactions.add("p,apple,15");
        transactions.add("r,apple,15");
    }

    @Test
    public void parse_validList_Ok() {
        List<FruitTransaction> actual = parser.parse(transactions);
        List<FruitTransaction> expected = getListOfFruitTransactions();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void parse_emptyList_Ok() {
        List<FruitTransaction> actual = parser.parse(new ArrayList<>());
        List<FruitTransaction> expected = List.of();
        Assert.assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void parse_nullList_NotOk() {
        parser.parse(null);
    }

    @Test (expected = NoSuchElementException.class)
    public void parse_invalidLetterOperation_NotOk() {
        transactions.set(2, "u,peach,32");
        parser.parse(transactions);
    }

    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void parse_invalidString_NotOk() {
        transactions.set(2, "b,peach");
        parser.parse(transactions);
    }

    @Test (expected = NumberFormatException.class)
    public void parse_notNumericalAmount_NotOk() {
        transactions.set(2, "s,melon,abc");
        parser.parse(transactions);
    }

    @After
    public void afterEachTest() {
        transactions.clear();
    }

    private List<FruitTransaction> getListOfFruitTransactions() {
        FruitTransaction bananaTransaction = FruitTransaction.of(
                FruitTransaction.Operation.BALANCE, BANANA, 25);
        FruitTransaction peachTransaction = FruitTransaction.of(
                FruitTransaction.Operation.SUPPLY, MANGO, 35);
        FruitTransaction appleTransaction = FruitTransaction.of(
                FruitTransaction.Operation.PURCHASE, APPLE, 15);
        FruitTransaction orangeTransaction = FruitTransaction.of(
                FruitTransaction.Operation.RETURN, ORANGE, 15);
        return List.of(bananaTransaction, peachTransaction, appleTransaction, orangeTransaction);

    }
}
