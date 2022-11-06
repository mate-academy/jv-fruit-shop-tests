package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParser;
import java.util.List;
import org.junit.Test;

public class FruitTransactionParserImplTest {
    private static final FruitTransactionParser parser = new FruitTransactionParserImpl();
    private static final int FIRST_TRANSACTION_INDEX = 0;

    @Test
    public void parseTransaction_Ok() {
        List<String> listToParse = List.of("type,fruit,quantity",
                "b,banana,20");
        FruitTransaction expectedTransaction = new FruitTransaction(
                FruitTransaction.Operation.getOperation("b"),
                new Fruit("banana"),
                20);
        assertEquals(expectedTransaction,
                parser.parseTransaction(listToParse).get(FIRST_TRANSACTION_INDEX));
    }

    @Test(expected = RuntimeException.class)
    public void parseTransaction_Null_notOk() {
        parser.parseTransaction(List.of(null));
    }
}
