package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParser;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class FruitTransactionParserImplTest {
    List<String> testStrings = List.of("_", "b,banana   ,100    ", "r     ,apple,50");
    List<FruitTransaction> testFruits;
    FruitTransactionParser parser;

    @Before
    public void setUp() throws Exception {
        Fruit banana = new Fruit("banana", 100);
        Fruit apple = new Fruit("apple", 50);
        FruitTransaction balanceOperation = new FruitTransaction(FruitTransaction.Operation.BALANCE, banana);
        FruitTransaction returnOperation = new FruitTransaction(FruitTransaction.Operation.RETURN, apple);
        testFruits = List.of(balanceOperation, returnOperation);
        parser = new FruitTransactionParserImpl();
    }

    @Test
    public void parse_OK() {
        List<FruitTransaction> fruitTransactions = parser.parse(testStrings);

        assertEquals(testFruits, fruitTransactions);
    }
}