package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParserService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.List;

public class ParserServiceTest {
    private static ParserService parserService;

    @BeforeClass
    public static void parserServiceInitialization() {
        parserService = new ParserServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void parseNull_notOk() {
        parserService.parse(null);
    }

    @Test(expected = RuntimeException.class)
    public void parseNotValidStrings_notOk() {
        List<String> readData = List.of(
                "type,fruit,quantity",
                "eat,candy,hundred");
        parserService.parse(readData);
    }

    @Test
    public void parseStringsToTransactions_Ok() {
        List<String> readData = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "s,banana,50",
                "p,banana,10",
                "r,banana,5");
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        new Fruit("banana", 20), 20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                        new Fruit("banana", 20), 50),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        new Fruit("banana", 70), 10),
                new FruitTransaction(FruitTransaction.Operation.RETURN,
                        new Fruit("banana", 60), 5));
        List<FruitTransaction> actual = parserService.parse(readData);
        Assert.assertEquals(expected, actual);
    }
}
