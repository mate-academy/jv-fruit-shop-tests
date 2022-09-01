package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParsingService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParsingServiceImplTest {
    private static final int FIRST_TRANSACTION = 0;
    private static final int SECOND_TRANSACTION = 1;
    private static ParsingService parsingService;

    @BeforeClass
    public static void beforeClass() {
        parsingService = new ParsingServiceImpl();
    }

    @Test
    public void twoValidLines_ok() {
        String firstLine = "s,apple,22";
        String secondLine = "p,banana,33";
        List<String> strings = new ArrayList<>();
        strings.add(firstLine);
        strings.add(secondLine);
        List<FruitTransaction> transactions = parsingService.createTransactions(strings);
        Assert.assertEquals("Expected size of List is 2", 2, transactions.size());
        Assert.assertEquals("Expected operation is SUPPLY",
                FruitTransaction.Operation.SUPPLY,
                transactions.get(FIRST_TRANSACTION).getOperation());
        Assert.assertEquals("Expected fruit is apple",
                "apple", transactions.get(FIRST_TRANSACTION).getFruit());
        Assert.assertEquals("Expected quantity of apple is 22", 22,
                transactions.get(FIRST_TRANSACTION).getQuantity());
        Assert.assertEquals("Expected operation is PURCHASE",
                FruitTransaction.Operation.PURCHASE,
                transactions.get(SECOND_TRANSACTION).getOperation());
        Assert.assertEquals("Expected fruit is banana",
                "banana", transactions.get(SECOND_TRANSACTION).getFruit());
        Assert.assertEquals("Expected quantity of banana is 33", 33,
                transactions.get(SECOND_TRANSACTION).getQuantity());
    }
}
