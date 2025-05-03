package core.basesyntax.service;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ParserTransactionsServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserTransactionServiceImplTest {
    private static ParserTransactionsService parserTransactionsService;

    @BeforeClass
    public static void beforeClass() {
        parserTransactionsService = new ParserTransactionsServiceImpl();
    }

    @Test
    public void parse_correctData_ok() {
        List<String> correctListForParsing = new ArrayList<>();
        correctListForParsing.add("type,fruit,quantity");
        correctListForParsing.add("b,banana,20");
        correctListForParsing.add("b,apple,100");
        correctListForParsing.add("s,banana,100");
        correctListForParsing.add("p,banana,13");
        correctListForParsing.add("r,apple,10");
        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, new Fruit("banana"), 20));
        expected.add(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, new Fruit("apple"), 100));
        expected.add(new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, new Fruit("banana"), 100));
        expected.add(new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, new Fruit("banana"), 13));
        expected.add(new FruitTransaction(
                FruitTransaction.Operation.RETURN, new Fruit("apple"), 10));
        List<FruitTransaction> actual
                = parserTransactionsService.parse(correctListForParsing);
        Assert.assertEquals("Incorrect parsing.", actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void parse_incorrectData_notOk() {
        List<String> incorrectListForParsing = new ArrayList<>();
        incorrectListForParsing.add("type,fruit,quantity");
        incorrectListForParsing.add("c,banana,20");
        parserTransactionsService.parse(incorrectListForParsing);
    }
}
