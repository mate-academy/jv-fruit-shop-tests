package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.parsing.TransactionParserImpl;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.Assert;
import org.junit.Test;

public class TransactionParserImplTest {
    @Test
    public void parse_balanceActualData_ok() {
        List<String> information = List.of("b,apple,200");
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 200);
        List<FruitTransaction> parsedInformation = new TransactionParserImpl().parse(information);
        Assert.assertEquals(transaction.getFruit(), parsedInformation.get(0).getFruit());
        Assert.assertEquals(transaction.getOperation(), parsedInformation.get(0).getOperation());
        Assert.assertEquals(transaction.getQuantity(), parsedInformation.get(0).getQuantity());
    }

    @Test
    public void parse_supplyActualData_ok() {
        List<String> information = List.of("s,apple,200");
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 200);
        List<FruitTransaction> parsedInformation = new TransactionParserImpl().parse(information);
        Assert.assertEquals(transaction.getFruit(), parsedInformation.get(0).getFruit());
        Assert.assertEquals(transaction.getOperation(), parsedInformation.get(0).getOperation());
        Assert.assertEquals(transaction.getQuantity(), parsedInformation.get(0).getQuantity());
    }

    @Test
    public void parse_returnActualData_ok() {
        List<String> information = List.of("r,apple,200");
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", 200);
        List<FruitTransaction> parsedInformation = new TransactionParserImpl().parse(information);
        Assert.assertEquals(transaction.getFruit(), parsedInformation.get(0).getFruit());
        Assert.assertEquals(transaction.getOperation(), parsedInformation.get(0).getOperation());
        Assert.assertEquals(transaction.getQuantity(), parsedInformation.get(0).getQuantity());
    }

    @Test
    public void parse_purchaseActualData_ok() {
        List<String> information = List.of("p,apple,200");
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 200);
        List<FruitTransaction> parsedInformation = new TransactionParserImpl().parse(information);
        Assert.assertEquals(transaction.getFruit(), parsedInformation.get(0).getFruit());
        Assert.assertEquals(transaction.getOperation(), parsedInformation.get(0).getOperation());
        Assert.assertEquals(transaction.getQuantity(), parsedInformation.get(0).getQuantity());
    }

    @Test(expected = NoSuchElementException.class)
    public void parse_wrongOperationTypeData_notOk() {
        List<String> information = List.of("n,apple,200");
        new TransactionParserImpl().parse(information);
    }

    @Test(expected = NullPointerException.class)
    public void parse_listIsNull_notOk() {
        List<String> list = null;
        new TransactionParserImpl().parse(list);
    }
}
