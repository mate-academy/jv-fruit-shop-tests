package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.ParserService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserServiceImplTest {
    private static ParserService transactionParserService;

    @BeforeClass
    public static void beforeClass() {
        transactionParserService = new ParserServiceImpl();
    }

    @Test
    public void parse_validDataSheet_ok() {
        Transaction transaction = new Transaction();
        transaction.setSum(20);
        transaction.setFruit(new Fruit("banana"));
        transaction.setOperation(Transaction.Operation.BALANCE);
        List<Transaction> expected = new ArrayList<>();
        expected.add(transaction);
        List<String> list = Arrays.asList("type,fruit,quantity",
                "b,banana,20");
        List<Transaction> actual = transactionParserService.parse(list);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void parse_empty_ok() {
        List<String> list = List.of("");
        List<Transaction> actual = transactionParserService.parse(list);
        Assert.assertTrue(actual.isEmpty());
    }
}
