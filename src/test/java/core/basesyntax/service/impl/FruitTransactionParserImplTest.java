package core.basesyntax.service.impl;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FruitTransactionParserImplTest {
    private static final String TEST_FRUIT_FIRST = "apple";
    private static final String TEST_FRUIT_SECOND = "banana";
    private static final int TEST_BALANCE_FIRST = 50;
    private static final int TEST_BALANCE_SECOND = 100;
    private static final FruitTransaction.Operation TEST_OPERATION = BALANCE;
    private FruitTransaction fruitTransactionFirst;
    private FruitTransaction fruitTransactionSecond;
    private List<FruitTransaction> fruitTransactionsList;
    private FruitTransactionParser fruitTransactionParser;

    @Before
    public void setUp() {
        fruitTransactionParser = new FruitTransactionParserImpl();
        fruitTransactionFirst = new FruitTransaction();
        fruitTransactionFirst.setOperation(TEST_OPERATION);
        fruitTransactionFirst.setFruit(TEST_FRUIT_FIRST);
        fruitTransactionFirst.setQuantity(TEST_BALANCE_FIRST);
        fruitTransactionSecond = new FruitTransaction();
        fruitTransactionSecond.setOperation(TEST_OPERATION);
        fruitTransactionSecond.setFruit(TEST_FRUIT_SECOND);
        fruitTransactionSecond.setQuantity(TEST_BALANCE_SECOND);
        fruitTransactionsList = new ArrayList<>();
        fruitTransactionsList.add(fruitTransactionFirst);
        fruitTransactionsList.add(fruitTransactionSecond);
    }

    @After
    public void tearDown() {
        fruitTransactionsList = null;
    }

    @Test
    public void getFruitTransactionList_Work_Ok() {
        List<String> dataFromCsv = new ArrayList<>();
        dataFromCsv.add("type,fruit,quantity");
        dataFromCsv.add("b,apple,50");
        dataFromCsv.add("b,banana,100");

        List<FruitTransaction> actual = fruitTransactionParser
                .getFruitTransactionsList(dataFromCsv);
        assertEquals(fruitTransactionsList, actual);
    }
}
