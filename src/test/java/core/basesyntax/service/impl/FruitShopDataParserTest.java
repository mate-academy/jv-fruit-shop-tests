package core.basesyntax.service.impl;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.Parser;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopDataParserTest {
    private static Parser parser;

    @BeforeClass
    public static void setUp() {
        parser = new FruitShopDataParser(new FruitShopRecordValidator());
    }

    @Test
    public void getTransaction_validData_Ok() {
        List<String> validData = List.of("type,fruit,quantity", "b,banana,20",
                "b,apple,100", "r,apple,10");
        assertThat(parser.getTransactions(validData), hasItems(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, new Fruit("banana"), 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, new Fruit("apple"), 100),
                new FruitTransaction(FruitTransaction.Operation.RETURN, new Fruit("apple"), 10)
        ));
    }

    @Test(expected = RuntimeException.class)
    public void getTransaction_invalidData_NotOk() {
        parser.getTransactions(List.of("type,fruit,quantity", "String without coma"));
    }
}
