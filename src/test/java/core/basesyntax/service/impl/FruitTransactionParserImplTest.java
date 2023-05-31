package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParser;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

public class FruitTransactionParserImplTest {
    private static final FruitTransactionParser parser = new FruitTransactionParserImpl();

    @Test
    public void parseTransaction_ok() {
        List<String> listToParse = List.of("type,fruit,quantity",
                "b,banana,20");
        List<FruitTransaction> expectedTransaction = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        new Fruit("banana"), 20));
        assertEquals(expectedTransaction, parser.parseTransaction(listToParse));
    }

    @Test
    public void parseTransaction_multipleData_ok() {
        List<String> listToParse = List.of("type,fruit,quantity",
                "b,banana,20",
                "s,banana,30",
                "b,orange,15");
        List<FruitTransaction> expectedTransactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        new Fruit("banana"), 20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                        new Fruit("banana"), 30),
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        new Fruit("orange"), 15));
        assertEquals(expectedTransactions, parser.parseTransaction(listToParse));
    }

    @Test
    public void parseTransaction_headerOnly_ok() {
        List<String> listToParse = List.of("type,fruit,quantity");
        assertEquals(Collections.emptyList(), parser.parseTransaction(listToParse));
    }
}
