package core.basesyntax.service;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TransactionParserServiceImplTest {
    @Rule
    public ExpectedException thrownRule = ExpectedException.none();

    @Test
    public void parse_balanceOperation_ok() {
        final TransactionParserService transactionParserService =
                new TransactionParserServiceImpl();
        final List<String> list = List.of("type,fruit,quantity", "b,banana,20");
        final List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        fruitTransactionList.add(fruitTransaction);
        Assert.assertEquals(fruitTransactionList, transactionParserService.parse(list));
    }

    @Test
    public void parse_supplyOperation_ok() {
        final TransactionParserService transactionParserService =
                new TransactionParserServiceImpl();
        final List<String> list = List.of("type,fruit,quantity", "s,apple,5");
        final List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(5);
        fruitTransactionList.add(fruitTransaction);
        Assert.assertEquals(fruitTransactionList, transactionParserService.parse(list));
    }

    @Test
    public void parse_purchaseOperation_ok() {
        final TransactionParserService transactionParserService =
                new TransactionParserServiceImpl();
        final List<String> list = List.of("type,fruit,quantity", "p,apple,5");
        final List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(5);
        fruitTransactionList.add(fruitTransaction);
        Assert.assertEquals(fruitTransactionList, transactionParserService.parse(list));
    }

    @Test
    public void parse_returnOperation_ok() {
        final TransactionParserService transactionParserService =
                new TransactionParserServiceImpl();
        final List<String> list = List.of("type,fruit,quantity", "r,apple,5");
        final List<FruitTransaction> fruitTransactionList = new ArrayList<>();
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(5);
        fruitTransactionList.add(fruitTransaction);
        Assert.assertEquals(fruitTransactionList, transactionParserService.parse(list));
    }

    @Test
    public void parse_skipTytleRow_Ok() {
        TransactionParserService transactionParserService = new TransactionParserServiceImpl();
        List<String> list = List.of("type,fruit,quantity", "type,apple,5");
        System.out.println(transactionParserService.parse(list));
    }

    @Test
    public void parse_incorrectOperation_notOk() {
        TransactionParserService transactionParserService = new TransactionParserServiceImpl();
        List<String> list = List.of("type,fruit,quantity", "x,apple,5");
        thrownRule.expect(RuntimeException.class);
        thrownRule.expectMessage("x - operation is not exist in enum");
        transactionParserService.parse(list);
    }
}
