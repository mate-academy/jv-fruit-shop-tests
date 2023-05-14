package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.TransactionParser;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TransactionParserImplTest {
    private static final int ZERO_INDEX = 0;
    private TransactionParser transactionParser;
    private FruitTransaction appleTransaction;

    @Before
    public void init() {
        Storage.fruits.put("apple",30);
        transactionParser = new TransactionParserImpl();
        appleTransaction = new FruitTransaction(Operation.SUPPLY, "apple", 20);
    }

    @Test
    public void parse_validData_Ok() {
        String appleString = "s,apple,20";
        List<FruitTransaction> expected = List.of(appleTransaction);
        List<FruitTransaction> actual = transactionParser.parse(List.of(appleString));
        //
        Operation expectedOperation = expected.get(ZERO_INDEX).getOperation();
        String expectedFruit = expected.get(ZERO_INDEX).getFruit();
        int expectedQuantity = expected.get(ZERO_INDEX).getQuantity();
        //
        Operation actualOperation = actual.get(ZERO_INDEX).getOperation();
        String actualFruit = actual.get(ZERO_INDEX).getFruit();
        int actualQuantity = actual.get(ZERO_INDEX).getQuantity();
        //
        assertEquals(expectedOperation, actualOperation);
        assertEquals(expectedFruit, actualFruit);
        assertEquals(expectedQuantity, actualQuantity);
    }
}
