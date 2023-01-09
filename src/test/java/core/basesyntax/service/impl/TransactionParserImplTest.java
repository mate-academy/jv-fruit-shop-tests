package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TransactionParserImplTest {
    @Rule
    public ExpectedException thrownRule = ExpectedException.none();

    @Test
    public void parse_OperationBalance_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(fruitTransaction);
        List<String> list = List.of("type;fruit;quantity", "b;banana;20");
        TransactionParser transactionParser = new TransactionParserImpl();
        Assert.assertEquals(fruitTransactionList, transactionParser.parse(list));
    }

    @Test
    public void parse_SupplyBalance_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(100);
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(fruitTransaction);
        List<String> list = List.of("type;fruit;quantity", "s;banana;100");
        TransactionParser transactionParser = new TransactionParserImpl();
        Assert.assertEquals(fruitTransactionList, transactionParser.parse(list));
    }

    @Test
    public void parse_PurchaseBalance_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(13);
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(fruitTransaction);
        TransactionParser transactionParser = new TransactionParserImpl();
        List<String> list = List.of("type;fruit;quantity", "p;banana;13");
        Assert.assertEquals(fruitTransactionList, transactionParser.parse(list));
    }

    @Test
    public void parse_ReturnBalance_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(10);
        TransactionParser transactionParser = new TransactionParserImpl();
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(fruitTransaction);
        List<String> list = List.of("type;fruit;quantity", "r;apple;10");
        Assert.assertEquals(fruitTransactionList, transactionParser.parse(list));
    }

    @Test
    public void parse_incorrectOperation_notOk() {
        TransactionParser transactionParser = new TransactionParserImpl();
        List<String> list = List.of("type;fruit;quantity", "x;apple;5");
        thrownRule.expect(RuntimeException.class);
        thrownRule.expectMessage("Can't find Operation type for this String ");
        transactionParser.parse(list);
    }

    @Test
    public void parse_skipTytleRow_Ok() {
        TransactionParser transactionParser = new TransactionParserImpl();
        List<String> list = List.of("type;fruit;quantity", "b;apple;5");
        System.out.println(transactionParser.parse(list));
    }
}
