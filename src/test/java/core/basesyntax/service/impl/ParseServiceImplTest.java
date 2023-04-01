package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParseServiceImplTest {
    private static ParseService parser;
    private static final String NAME_FRUIT = "banana";
    private static final String OPERATION = "b";

    @BeforeClass
    public static void beforeClass() {
        parser = new ParseServiceImpl();
    }

    @Test
    public void getFruitTransactions_ok() {
        List<String> fruitTransactions = new ArrayList<>();
        fruitTransactions.add("b,banana,80");
        List<FruitTransaction> fruitTransactionList = parser
                .getFruitTransactions(fruitTransactions);
        FruitTransaction actual = fruitTransactionList.get(0);
        FruitTransaction expected = new FruitTransaction(OPERATION, NAME_FRUIT, 80);
        assertEquals(actual, expected);
    }

    @Test(expected = NullPointerException.class)
    public void getFruitTransactions_nullList_notOk() {
        parser.getFruitTransactions(null);
    }
}
