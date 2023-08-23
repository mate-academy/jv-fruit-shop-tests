package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class FruitTransactionParserImplTest {
    private FruitTransactionParser fruitTransactionParser = new FruitTransactionParserImpl();

    @Test
    void getFruitTransactionsListOk() {
        List<String> transactions = new ArrayList<>();
        transactions.add("b,banana,120");
        transactions.add("b,apple,220");
        transactions.add("b,waterlemon,70");
        List<FruitTransaction> actualResult = fruitTransactionParser
                .getFruitTransactionsList(transactions);
        Assert.assertNotNull(actualResult);
        String actualResult2 = transactions.get(2);
        Assert.assertEquals("b,waterlemon,70", actualResult2);
    }
}
