package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.model.Operation;
import core.basesyntax.service.ParseService;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParseServiceImplTest {
    private static ParseService parser;

    @BeforeClass
    public static void beforeClass() {
        parser = new ParseServiceImpl();
    }

    @Test
    public void getFruitTransactions_ok() {
        List<String> fruitTransactions = new ArrayList<>();
        fruitTransactions.add("b,banana,80");
        fruitTransactions.add("s,banana,10");
        Operation actual = parser.getFruitTransactions(fruitTransactions).get(1).getOperation();
        Operation expected = Operation.SUPPLY;
        assertEquals(actual, expected);
    }

    @Test
    public void getFruitTransactions_nullList_notOk() {
        try {
            parser.getFruitTransactions(null);
        } catch (NullPointerException e) {
            return;
        }
        fail("There is no data");
    }
}
