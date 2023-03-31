package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.model.FruitTransaction;
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
        List<FruitTransaction> fruitTransactionList = parser
                .getFruitTransactions(fruitTransactions);
        Operation actualOperation = fruitTransactionList.get(0).getOperation();
        String actualFruit = fruitTransactionList.get(0).getFruit();
        int actualQuantity = fruitTransactionList.get(0).getQuantity();
        FruitTransaction expected = new FruitTransaction("b", "banana", 80);
        Operation expectedOperation = expected.getOperation();
        String expectedFruit = expected.getFruit();
        int expectedQuantity = expected.getQuantity();
        assertEquals(actualOperation, expectedOperation);
        assertEquals(actualFruit, expectedFruit);
        assertEquals(actualQuantity, expectedQuantity);
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
