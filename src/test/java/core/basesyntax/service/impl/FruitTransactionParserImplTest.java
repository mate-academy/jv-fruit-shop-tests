package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParser;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FruitTransactionParserImplTest {
    private static FruitTransactionParser fruitTransactionParser;

    @Before
    public void setUp() {
        fruitTransactionParser = new FruitTransactionParserImpl();
    }

    @Test
    public void parse_invalidData_ok() {
        List<String> testData = new ArrayList<>();
        testData.add("type,fruit,quantity");

        List<FruitTransaction> expected = new ArrayList<>();
        List<FruitTransaction> actual = fruitTransactionParser.parse(testData);
        assertEquals(expected, actual);
    }

//    @Test
//    public void parse_validData_ok() {
//        List<String> testData = new ArrayList<>();
//        testData.add("type,fruit,quantity");
//        testData.add("b,apple,100");
//        testData.add("r,apple,10");
//        testData.add("s,apple,1");
//        testData.add("b,banana,2");
//        testData.add("p,banana,20");
//        testData.add("s,banana,200");
//
//        List<FruitTransaction> expected = List.of(
//                new FruitTransaction(FruitTransaction.Operation.BALANCE, new Fruit("apple"), 100),
//                new FruitTransaction(FruitTransaction.Operation.RETURN, new Fruit("apple"), 10),
//                new FruitTransaction(FruitTransaction.Operation.SUPPLY, new Fruit("apple"), 1),
//                new FruitTransaction(FruitTransaction.Operation.BALANCE,new Fruit("banana"),2),
//                new FruitTransaction(FruitTransaction.Operation.SUPPLY, new Fruit("banana"), 20),
//                new FruitTransaction(FruitTransaction.Operation.PURCHASE, new Fruit("banana"),200));
//        List<FruitTransaction> actual = fruitTransactionParser.parse(testData);
//        assertEquals(expected, actual);
//    }

    @Test
    public void parse_emptyData_ok() {
        List<FruitTransaction> actual = fruitTransactionParser.parse(new ArrayList<>());
        List<FruitTransaction> expected = List.of();
        assertEquals(expected, actual);
    }
}