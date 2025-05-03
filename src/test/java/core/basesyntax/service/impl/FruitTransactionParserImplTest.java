package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionParserImplTest {
    private static FruitTransactionParser fruitTransactionParser;

    @BeforeClass
    public static void init() {
        fruitTransactionParser = new FruitTransactionParserImpl();
    }

    @Test
    public void transformToTransaction_validTransactionList_ok() {
        List<String> dataFromFile = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,30",
                "s,apple,70",
                "p,apple,50",
                "p,banana,10");
        List<FruitTransaction> actual = fruitTransactionParser
                .transformToTransaction(dataFromFile);
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 30),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 70),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 50),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 10));
        assertEquals("Wrong return result for: " + dataFromFile, expected, actual);
    }

    @Test
    public void transformToTransaction_emptyList_ok() {
        List<String> dataFromFile = new ArrayList<>();
        List<FruitTransaction> actual = fruitTransactionParser
                .transformToTransaction(dataFromFile);
        List<FruitTransaction> expected = new ArrayList<>();
        assertEquals("Wrong return result for: " + dataFromFile, expected, actual);
    }
}
