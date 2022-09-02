package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class FruitTransactionParserImplTest {
    private static FruitTransactionParser parser;
    private List<String> transactionStrings;

    @BeforeClass
    public static void beforeClass() {
        parser = new FruitTransactionParserImpl();
    }

    @Before
    public void setUp() {
        transactionStrings = List.of
                ("type,fruit,quantity", "b,banana,18", "s,peach,35", "r,apple,6", "p,mango,7");
    }

    @Test
    public void parseValidList_Ok() {
        List<FruitTransaction> actual = parser.parse(transactionStrings);
        List<FruitTransaction> expected = getListOfFruitTransactions();
        Assert.assertEquals(expected, actual);
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