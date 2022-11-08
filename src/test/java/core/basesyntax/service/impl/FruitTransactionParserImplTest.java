package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParser;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionParserImplTest {
    private static FruitTransactionParser fruitTransactionParser;

    @BeforeClass
    public static void setUp() {
        fruitTransactionParser = new FruitTransactionParserImpl();
    }

    @Test
    public void parse_headerOnly_ok() {
        List<String> testData = new ArrayList<>();
        testData.add("type,fruit,quantity");

        List<FruitTransaction> expected = Collections.emptyList();
        List<FruitTransaction> actual = fruitTransactionParser.parse(testData);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_validData_ok() {
        List<String> testData = new ArrayList<>();
        testData.add("type,fruit,quantity");
        testData.add("b,apple,50");
        testData.add("r,apple,11");
        testData.add("s,apple,123");
        testData.add("b,banana,278");
        testData.add("p,banana,20");
        testData.add("s,kiwi,270");

        Fruit apple = new Fruit("apple");
        Fruit kiwi = new Fruit("kiwi");
        Fruit banana = new Fruit("banana");

        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, apple, 50),
                new FruitTransaction(FruitTransaction.Operation.RETURN, apple, 11),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, apple, 123),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, banana,278),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, banana, 20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, kiwi,270));
        List<FruitTransaction> actual = fruitTransactionParser.parse(testData);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_emptyData_ok() {
        List<FruitTransaction> actual = fruitTransactionParser.parse(new ArrayList<>());
        List<FruitTransaction> expected = Collections.emptyList();
        assertEquals(expected, actual);
    }
}
