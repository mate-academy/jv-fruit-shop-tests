package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.ParsedService;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParsedServiceTest {
    private static ParsedService parsedService;
    private static List<Transaction> transactions;

    @BeforeClass
    public static void beforeClass() {
        parsedService = new ParsedServiceImpl();
        transactions = new ArrayList<>();
        transactions.add(new Transaction(Transaction.Operation.BALANCE, new Fruit("banana"), 20));
        transactions.add(new Transaction(Transaction.Operation.SUPPLY, new Fruit("banana"), 100));
        transactions.add(new Transaction(Transaction.Operation.RETURN, new Fruit("apple"), 10));
        transactions.add(new Transaction(Transaction.Operation.PURCHASE, new Fruit("banana"), 13));
    }

    @Test
    public void parsedServiceIsValid_Ok() {
        List<String> data = new ArrayList<>();
        data.add("type,fruit,quantity");
        data.add("b,banana,20");
        data.add("s,banana,100");
        data.add("r,apple,10");
        data.add("p,banana,13");
        List<Transaction> expected = transactions;
        List<Transaction> actual = parsedService.parse(data);
        assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() {
        Storage.getStorage().clear();
    }
}
