package core.basesyntax.service;

import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitParserTest {
    private static FruitParser parser;

    @BeforeClass
    public static void beforeClass() throws Exception {
        parser = new FruitParser();
    }

    @Test
    public void parser_correctData_OK() {
        String data = "s,apple,13";
        Transaction expected = new Transaction(Transaction.Operation.SUPPLY,
                new Fruit("apple"), 13);
        Transaction actual = parser.parseData(data);
        Assert.assertEquals(expected, actual);
    }
}
