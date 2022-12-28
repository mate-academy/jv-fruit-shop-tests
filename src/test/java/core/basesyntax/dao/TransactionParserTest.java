package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class TransactionParserTest {

    public List<FruitTransaction> getDefaultListFruitTransaction() {
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

    @Test
    public void parseTransaction_Ok() {
        TransactionParser transactionParser = new TransactionParserImpl();
        String data = "b,banana,20\n"
                + "b,apple,100\n"
                + "s,banana,100\n";
        List<FruitTransaction> expectedListTransaction = getDefaultListFruitTransaction();
        List<FruitTransaction> actualTransactionList = transactionParser.parseTransactions(data);
        assertEquals(expectedListTransaction, actualTransactionList);
    }

    @Test
    public void parseTransaction_nullValue_NotOk() {
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
}
