package core.basesyntax.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionParserImplTest {
    private static FruitTransactionParserImpl parser;
    private List<String> transactionStrings;

    @BeforeClass
    public static void beforeClass() {
        parser = new FruitTransactionParserImpl();
    }

    @Before
    public void setUp() {
        transactionStrings = new ArrayList<>();
        transactionStrings.add("type,fruit,quantity");
        transactionStrings.add("b,banana,18");
        transactionStrings.add("s,peach,35");
        transactionStrings.add("r,apple,6");
        transactionStrings.add("p,mango,7");
    }

    @Test
    public void parseValidList_ok() {
        List<FruitTransaction> actual = parser.parse(transactionStrings);
        List<FruitTransaction> expected = getListOfFruitTransactions();
        assertEquals(expected, actual);
    }

    @Test
    public void parseEmptyList_ok() {
        List<FruitTransaction> actual = parser.parse(new ArrayList<>());
        List<FruitTransaction> expected = List.of();
        assertEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void parseNullList_notOk() {
        parser.parse(null);
    }

    @Test (expected = NoSuchElementException.class)
    public void parseInvalidLetterOperation_notOk() {
        transactionStrings.set(2, "u,kiwi,32");
        parser.parse(transactionStrings);
    }

    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void parseInvalidString_notOk() {
        transactionStrings.set(2, "b,kiwi");
        parser.parse(transactionStrings);
    }

    @After
    public void clearTransaction() {
        transactionStrings.clear();
    }

    private List<FruitTransaction> getListOfFruitTransactions() {
        List<FruitTransaction> fruitTransactions = new ArrayList<>();
        FruitTransaction bananaTransaction = FruitTransaction.of(
                FruitTransaction.Operation.BALANCE, "banana", 18);
        FruitTransaction peachTransaction = FruitTransaction.of(
                FruitTransaction.Operation.SUPPLY, "peach", 35);
        FruitTransaction appleTransaction = FruitTransaction.of(
                FruitTransaction.Operation.RETURN, "apple", 6);
        FruitTransaction mangoTransaction = FruitTransaction.of(
                FruitTransaction.Operation.PURCHASE, "mango", 7);
        fruitTransactions.add(bananaTransaction);
        fruitTransactions.add(peachTransaction);
        fruitTransactions.add(appleTransaction);
        fruitTransactions.add(mangoTransaction);
        return fruitTransactions;
    }
}
