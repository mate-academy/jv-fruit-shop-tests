package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitTransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionParserImplTest {
    private static List<String> fileData;
    private static FruitTransactionParser fruitTransactionParser;

    @BeforeClass
    public static void setUp() {
        fileData = new ArrayList<>();
        fileData.add("type,fruit,quantity");
        fileData.add("b,banana,20");
        fileData.add("s,banana,150");
        fileData.add("p,banana,18");
        fileData.add("r,banana,0");
        fruitTransactionParser = new FruitTransactionParserImpl();
    }

    @Test
    public void toTransactions_validResult_ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(Operation.SUPPLY,
                "banana", 150));
        expected.add(new FruitTransaction(Operation.PURCHASE, "banana", 18));
        expected.add(new FruitTransaction(Operation.RETURN, "banana", 0));
        List<FruitTransaction> actual = fruitTransactionParser.toTransactions(fileData);
        assertEquals(expected, actual);
    }

    @Test
    public void toTransactions_invalidResult_notOk() {
        List<FruitTransaction> expected = new ArrayList<>();
        List<FruitTransaction> actual = fruitTransactionParser.toTransactions(fileData);
        assertNotEquals(expected, actual);
    }
}
