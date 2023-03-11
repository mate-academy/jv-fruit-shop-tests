package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.TransactionParserImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionParserTest {
    private static TransactionParser transactionParser;
    private static List<String> correct;
    private static List<FruitTransaction> expected;

    @BeforeClass
    public static void beforeAll() {
        transactionParser = new TransactionParserImpl();
        correct = initTransactionList();
        expected = initFruitTransactionList();
    }

    @Test
    public void parseTransaction_correctParsing_ok() {
        List<FruitTransaction> actual = transactionParser.parse(correct);
        Assert.assertEquals("Wrong parsing", expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parseTransaction_wrongOperationCode_notOk() {
        List<String> listWithWrongCodeOperator = new ArrayList<>(correct);
        listWithWrongCodeOperator.add("        wrong_code,banana,256");
        transactionParser.parse(listWithWrongCodeOperator);
    }

    @Test(expected = NumberFormatException.class)
    public void parseTransaction_wrongTypeDataOfQuantity_notOk() {
        List<String> listWithWrongCodeOperator = new ArrayList<>(correct);
        listWithWrongCodeOperator.add("        s,banana,stringInsteadNumber");
        transactionParser.parse(listWithWrongCodeOperator);
    }

    private static List<String> initTransactionList() {
        return List.of(
                "        type,fruit,quantity",
                "        b,banana,20",
                "        b,apple,100",
                "        s,banana,100",
                "        p,banana,13",
                "        r,apple,10",
                "        p,apple,20",
                "        p,banana,5",
                "        s,banana,50");
    }

    private static List<FruitTransaction> initFruitTransactionList() {
        return List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50));
    }
}
