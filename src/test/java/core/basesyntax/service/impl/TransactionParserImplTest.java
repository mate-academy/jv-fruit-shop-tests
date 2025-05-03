package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParser;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TransactionParserImplTest {
    private static final List<String> DATA_FROM_FILE = List.of(
             "b,banana,20",
             "b,apple,100",
             "s,banana,100",
             "p,banana,13",
             "r,apple,10",
             "p,apple,20",
             "p,banana,5",
             "s,banana,50");
    private TransactionParser parser;
   
    @Before
    public void setUp() {
        parser = new TransactionParserImpl(); 
    }

    @Test
    public void transactionParser_Ok() {
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50));
        List<FruitTransaction> actual = parser.parse(DATA_FROM_FILE);
        assertEquals(expected, actual);
    }
}
