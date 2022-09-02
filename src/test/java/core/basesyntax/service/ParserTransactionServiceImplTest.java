package core.basesyntax.service;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.ParserTransactionsServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ParserTransactionServiceImplTest {
    public static final String SEPARATOR = ",";
    public static final int OPERATION_INDEX = 0;
    public static final int NAME_INDEX = 1;
    public static final int AMOUNT_INDEX = 2;

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
                = new ParserTransactionsServiceImpl().parse(correctListForParsing);
        Assert.assertEquals(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void parse_incorrectData_notOk() {
        List<String> incorrectListForParsing = new ArrayList<>();
        incorrectListForParsing.add("type,fruit,quantity");
        incorrectListForParsing.add("c,banana,20");
        new ParserTransactionsServiceImpl().parse(incorrectListForParsing);

    }
}
