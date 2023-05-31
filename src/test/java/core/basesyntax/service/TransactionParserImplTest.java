package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.parsing.TransactionParser;
import core.basesyntax.service.parsing.TransactionParserImpl;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionParserImplTest {
    private static TransactionParser transactionParser;

    @BeforeClass
    public static void beforeClass() {
        transactionParser = new TransactionParserImpl();
    }

    @Test
    public void parse_balanceActualData_ok() {
        List<String> information = List.of("b,apple,200");
        FruitTransaction expected = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 200);
        List<FruitTransaction> actual = transactionParser.parse(information);
        Assert.assertEquals(expected, actual.get(0));
    }

    @Test
    public void parse_supplyActualData_ok() {
        List<String> information = List.of("s,apple,200");
        FruitTransaction expected = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 200);
        List<FruitTransaction> actual = transactionParser.parse(information);
        Assert.assertEquals(expected, actual.get(0));
    }

    @Test
    public void parse_returnActualData_ok() {
        List<String> information = List.of("r,apple,200");
        FruitTransaction expected = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", 200);
        List<FruitTransaction> actual = transactionParser.parse(information);
        Assert.assertEquals(expected, actual.get(0));
    }

    @Test
    public void parse_purchaseActualData_ok() {
        List<String> information = List.of("p,apple,200");
        FruitTransaction expected = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 200);
        List<FruitTransaction> actual = transactionParser.parse(information);
        Assert.assertEquals(expected, actual.get(0));
    }

    @Test(expected = NoSuchElementException.class)
    public void parse_wrongOperationTypeData_notOk() {
        List<String> information = List.of("n,apple,200");
        transactionParser.parse(information);
    }

    @Test(expected = NullPointerException.class)
    public void parse_listIsNull_notOk() {
        List<String> list = null;
        transactionParser.parse(list);
    }
}
