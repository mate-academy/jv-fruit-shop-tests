package core.basesyntax.service.impl;

import core.basesyntax.model.ActivityType;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionParserImplTest {
    private static final List<String> CORRECT_DATA_FROM_FILE = List.of(
            "type,fruit,quantity",
            "b,banana,20",
            "b,apple,100");
    private static FruitTransactionParser fruitTransactionParser;

    @BeforeClass
    public static void beforeClass() {
        fruitTransactionParser = new FruitTransactionParserImpl();
    }

    @Test(expected = RuntimeException.class)
    public void parse_dataFromFileIsNull_notOk() {
        fruitTransactionParser.parse(null);
    }

    @Test
    public void parse_successfulParsing_ok() {
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(
                ActivityType.BALANCE, new Fruit("banana"), 20));
        expected.add(new FruitTransaction(
                ActivityType.BALANCE, new Fruit("apple"), 100));
        Assert.assertEquals(expected, fruitTransactionParser.parse(CORRECT_DATA_FROM_FILE));
    }
}
