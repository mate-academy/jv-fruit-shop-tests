package core.basesyntax.service.impl;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Test;

public class FruitTransactionParserImplTest {
    private static final String KEY_APPLE = "apple";
    private static final String KEY_BANANA = "banana";
    private static final FruitTransactionParser fruitTransactionParser =
            new FruitTransactionParserImpl();

    @Test
    public void parseData_isOk() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,200", "s,apple,100",
                "p,banana,100", "r,apple,0");
        List<FruitTransaction> actualFruitTransactions = new ArrayList<>(List.of(
                        new FruitTransaction(BALANCE, KEY_BANANA,200),
                        new FruitTransaction(SUPPLY, KEY_APPLE,100),
                        new FruitTransaction(PURCHASE, KEY_BANANA,100),
                        new FruitTransaction(RETURN, KEY_APPLE,0)));
        assertEquals(fruitTransactionParser.parseData(expected),
                actualFruitTransactions);
    }

    @After
    public void tearDown() {
        FruitStorage.storage.clear();
    }
}
