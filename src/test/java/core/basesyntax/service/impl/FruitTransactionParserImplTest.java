package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class FruitTransactionParserImplTest {
    private static FruitTransactionParser parser;
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
    public void parseValidList_Ok() {
        List<FruitTransaction> actual = parser.parse(transactionStrings);
        List<FruitTransaction> expected = getListOfFruitTransactions();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void parseEmptyList_Ok() {
        List<FruitTransaction> actual = parser.parse(new ArrayList<>());
        List<FruitTransaction> expected = List.of();
        Assert.assertEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void parseNull_NotOk() {
        parser.parse(null);
    }

    @Test (expected = NoSuchElementException.class)
    public void parseOperationWithInvalidLetter_NotOk() {
        transactionStrings.set(2, "u,kiwi,32");
        parser.parse(transactionStrings);
    }

    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void parseListWithMissingData_NotOk() {
        transactionStrings.set(2, "b,kiwi");
        parser.parse(transactionStrings);
    }

    @Test (expected = NumberFormatException.class)
    public void parseAmountFromNotNumberValue_NotOk() {
        transactionStrings.set(2, "s,peach,abc");
        parser.parse(transactionStrings);
    }

    @After
    public void afterEachTest() {
        transactionStrings.clear();
    }

    private List<FruitTransaction> getListOfFruitTransactions() {
        FruitTransaction bananaTransaction = FruitTransaction.of
                (FruitTransaction.Operation.BALANCE, "banana", 18);
        FruitTransaction peachTransaction = FruitTransaction.of
                (FruitTransaction.Operation.SUPPLY, "peach", 35);
        FruitTransaction appleTransaction = FruitTransaction.of
                (FruitTransaction.Operation.RETURN, "apple", 6);
        FruitTransaction mangoTransaction = FruitTransaction.of
                (FruitTransaction.Operation.PURCHASE, "mango", 7);
        return List.of(bananaTransaction, peachTransaction, appleTransaction, mangoTransaction);
    }
}
