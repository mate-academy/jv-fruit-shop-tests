package core.basesyntax.service.impl;

import static org.junit.Assert.fail;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionParserImplTest {
    private static FruitTransactionParserImpl fruitTransactionParser;

    @BeforeClass
    public static void beforeAll() {
        fruitTransactionParser = new FruitTransactionParserImpl();
    }

    @Test
    public void parseData_Ok() {
        List<String> data = List.of("type,fruit,quantity", "b,apple,50",
                "s,banana,250", "p,apple,20", "r,apple,20");
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 50),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 250),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 20)
        );
        String actual = fruitTransactionParser.toTransactions(data).toString();
        Assert.assertNotEquals("Data wasn't parsed correctly.", expected.toString(), actual);
    }

    @Test
    public void parseData_NotOk() {
        List<String> data = List.of("type,fruit,quantity", "b,apple,-60");
        try {
            fruitTransactionParser.toTransactions(data);
        } catch (RuntimeException e) {
            return;
        }
        fail("parseData method should throw an RuntimeException for wrong input data.");
    }
}
