package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class TransactionParserTest {

    public List<FruitTransaction> fruitTransactionForFirstCase() {
        FruitTransaction firstFruitTransaction = new FruitTransaction();
        firstFruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        firstFruitTransaction.setFruit("banana");
        firstFruitTransaction.setQuantity(20);
        FruitTransaction secondFruitTransaction = new FruitTransaction();
        secondFruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        secondFruitTransaction.setFruit("apple");
        secondFruitTransaction.setQuantity(100);
        FruitTransaction thirdFruitTransaction = new FruitTransaction();
        thirdFruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        thirdFruitTransaction.setFruit("banana");
        thirdFruitTransaction.setQuantity(100);
        List<FruitTransaction> defaultList = new ArrayList<>();
        defaultList.add(firstFruitTransaction);
        defaultList.add(secondFruitTransaction);
        defaultList.add(thirdFruitTransaction);
        return defaultList;
    }

    public List<FruitTransaction> fruitTransactionForSecondCase() {
        FruitTransaction firstFruitTransaction = new FruitTransaction();
        firstFruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        firstFruitTransaction.setFruit("apple");
        firstFruitTransaction.setQuantity(40);
        FruitTransaction secondFruitTransaction = new FruitTransaction();
        secondFruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        secondFruitTransaction.setFruit("orange");
        secondFruitTransaction.setQuantity(80);
        FruitTransaction thirdFruitTransaction = new FruitTransaction();
        thirdFruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        thirdFruitTransaction.setFruit("orange");
        thirdFruitTransaction.setQuantity(10);
        FruitTransaction fourthFruitTransaction = new FruitTransaction();
        fourthFruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fourthFruitTransaction.setFruit("apple");
        fourthFruitTransaction.setQuantity(10);
        List<FruitTransaction> defaultList = new ArrayList<>();
        defaultList.add(firstFruitTransaction);
        defaultList.add(secondFruitTransaction);
        defaultList.add(thirdFruitTransaction);
        defaultList.add(fourthFruitTransaction);
        return defaultList;
    }

    @Test
    public void parseTransaction_firstCase_Ok() {
        TransactionParser transactionParser = new TransactionParserImpl();
        String data = "b,banana,20\n"
                + "b,apple,100\n"
                + "s,banana,100\n";
        List<FruitTransaction> expectedListTransaction = fruitTransactionForFirstCase();
        List<FruitTransaction> actualTransactionList = transactionParser.parseTransactions(data);
        assertEquals(expectedListTransaction, actualTransactionList);
    }

    @Test
    public void parseTransaction_secondCase_Ok() {
        TransactionParser transactionParser = new TransactionParserImpl();
        String data = "b,apple,40\n"
                + "b,orange,80\n"
                + "s,orange,10\n"
                + "s,apple,10\n";
        List<FruitTransaction> expectedListTransaction = fruitTransactionForSecondCase();
        List<FruitTransaction> actualTransactionList = transactionParser.parseTransactions(data);
        assertEquals(expectedListTransaction, actualTransactionList);
    }

    @Test
    public void parseTransaction_nullInputValue_NotOk() {
        TransactionParser transactionParser = new TransactionParserImpl();
        String data = null;
        assertThrows(NullPointerException.class, () -> transactionParser.parseTransactions(data));
    }

    @Test
    public void parseTransaction_emptyValue_notOk() {
        TransactionParser transactionParser = new TransactionParserImpl();
        String data = "";
        assertThrows(RuntimeException.class, () -> transactionParser.parseTransactions(data));
    }

    @Test
    public void parseTransaction_incompleteValue_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        List<FruitTransaction> expectedListTransaction = new ArrayList<>();
        expectedListTransaction.add(fruitTransaction);
        TransactionParser transactionParser = new TransactionParserImpl();
        String data = "b,banana,20";
        List<FruitTransaction> actualTransactionList = transactionParser.parseTransactions(data);
        assertEquals(expectedListTransaction, actualTransactionList);
    }
}
