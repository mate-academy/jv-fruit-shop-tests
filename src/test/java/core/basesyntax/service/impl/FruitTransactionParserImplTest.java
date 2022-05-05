package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParser;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FruitTransactionParserImplTest {
    private List<String> testStrings = List.of("_", "b,banana   ,100    ", "r     ,apple,50");
    private List<FruitTransaction> testFruits;
    private FruitTransactionParser parser;
    private List<String> testEmptyString = Collections.emptyList();

    @Before
    public void setUp() {
        parser = new FruitTransactionParserImpl();
        Fruit banana = new Fruit("banana", 100);
        Fruit apple = new Fruit("apple", 50);
        FruitTransaction balanceOperation =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, banana);
        FruitTransaction returnOperation =
                new FruitTransaction(FruitTransaction.Operation.RETURN, apple);
        testFruits = List.of(balanceOperation, returnOperation);
    }

    @Test
    public void parse_OK() {
        List<FruitTransaction> actual = parser.parse(testStrings);
        assertEquals(testFruits, actual);
    }

    @Test
    public void parse_getSize_Ok() {
        List<FruitTransaction> actual = parser.parse(testStrings);
        assertEquals(testFruits.size(), actual.size());
    }

    @Test
    public void getOperation_notOk() {
        try {
            FruitTransaction.Operation.BALANCE.getOperationFromString("d");
        } catch (RuntimeException e) {
            return;
        }
        fail("This operation is not allowed");
    }

    @Test
    public void parse_EmptyLines_notOk() {
        try {
            parser.parse(testEmptyString);
        } catch (RuntimeException e) {
            return;
        }
        fail("This operation is not allowed");
    }
}
