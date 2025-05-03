package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParser;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TransactionParserImplTest {
    private static TransactionParser transactionParser;

    @Rule
    public ExpectedException thrownRule = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        transactionParser = new TransactionParserImpl();
    }

    @Test
    public void parse_OperationBalance_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(fruitTransaction);
        List<String> list = List.of("type;fruit;quantity", "b;banana;20");
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
        List<String> list = List.of("type;fruit;quantity", "p;banana;13");
        Assert.assertEquals(fruitTransactionList, transactionParser.parse(list));
    }

    @Test
    public void parse_ReturnBalance_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(10);
        List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        fruitTransactionList.add(fruitTransaction);
        List<String> list = List.of("type;fruit;quantity", "r;apple;10");
        Assert.assertEquals(fruitTransactionList, transactionParser.parse(list));
    }

    @Test
    public void parse_incorrectOperation_notOk() {
        List<String> list = List.of("type;fruit;quantity", "x;apple;5");
        thrownRule.expect(RuntimeException.class);
        thrownRule.expectMessage("Can't find Operation type for this String ");
        transactionParser.parse(list);
    }

    @Test
    public void parse_skipTitleRow_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setQuantity(5);
        fruitTransaction.setFruit("apple");
        List<FruitTransaction> expectedList = List.of(fruitTransaction);
        List<String> list = List.of("type;fruit;quantity", "b;apple;5");
        List<FruitTransaction> actualList = transactionParser.parse(list);
        Assert.assertEquals(expectedList, actualList);
    }
}
