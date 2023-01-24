package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitReport;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitTransactionParserService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FruitTransactionParserServiceImplTest {
    private FruitTransactionParserService fruitTransactionParserService =
            new FruitTransactionParserServiceImpl();

    @Before
    public void cleanBefore() {
        Storage.fruits.clear();
    }

    @After
    public void cleanAfter() {
        Storage.fruits.clear();
    }

    @Test
    public void fruitTransactionParserServiceImplTest_Ok() throws IOException {
        List<FruitTransaction> list = new ArrayList<>();
        list.add(new FruitTransaction(Operation.BALANCE, "pineapple", 300));
        list.add(new FruitTransaction(Operation.BALANCE, "strawberry", 50));
        list.add(new FruitTransaction(Operation.SUPPLY, "pineapple", 100));
        list.add(new FruitTransaction(Operation.PURCHASE, "strawberry", 20));
        list.add(new FruitTransaction(Operation.PURCHASE, "pineapple", 200));
        list.add(new FruitTransaction(Operation.RETURN, "strawberry", 15));

        List<FruitReport> expectedList = new ArrayList<>();
        expectedList.add(new FruitReport("pineapple", 200));
        expectedList.add(new FruitReport("strawberry", 45));

        List<FruitReport> actualList = fruitTransactionParserService.prepareDataForReport(list);

        String expected = expectedList.stream().map(i -> String.valueOf(i.getFruit())
                + String.valueOf(i.getQuantity())).collect(Collectors.joining());
        String actual = actualList.stream().map(i -> String.valueOf(i.getFruit())
                + String.valueOf(i.getQuantity())).collect(Collectors.joining());
        Assert.assertEquals(actual, expected);

    }
}
