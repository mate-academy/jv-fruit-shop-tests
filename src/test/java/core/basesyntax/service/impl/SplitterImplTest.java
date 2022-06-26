package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.Splitter;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class SplitterImplTest {
    private static Splitter splitter;

    @BeforeClass
    public static void beforeAllTestMethods() {
        splitter = new SplitterImpl();
    }

    @Test
    public void createTransactionList_ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100));
        expected.add(new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 10));
        expected.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 35));
        expected.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100));
        List<String> info = new ArrayList<>(List.of("type,fruit,quantity",
                "b,banana,100", "r,banana,10", "p,banana,35", "s,banana,100"));
        List<FruitTransaction> actual = splitter.createTransactionList(info);

        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void createTransactionList_invalidInfo_notOk() {
        List<String> info = new ArrayList<>(List.of("type,fruit,quantity",
                "l,banana,100", "n,banana,10"));
        List<FruitTransaction> actual = splitter.createTransactionList(info);

    }
}
