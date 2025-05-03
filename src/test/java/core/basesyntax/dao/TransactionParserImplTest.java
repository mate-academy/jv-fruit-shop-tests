package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.Test;

class TransactionParserImplTest {
    @Test
    void parseCorrectTransaction_ok() {
        List<String> transactionList = List.of("s,banana,100");
        FruitTransaction expected = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 100);
        TransactionParser transactionParser = new TransactionParserImpl();
        FruitTransaction actual = transactionParser.parseTransactions(transactionList).get(0);
        assertEquals(expected, actual);
    }

    @Test
    void parseListOfTransactions_ok() {
        List<String> transactionList = List.of(
                "b,banana,20",
                "p,banana,5",
                "s,banana,50");
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,"banana",20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50));
        List<FruitTransaction> actual =
                new TransactionParserImpl().parseTransactions(transactionList);
        assertEquals(expected, actual);
    }

    @Test
    void parseWrongTransactionCode_notOk() {
        List<String> transactionList = List.of("x,banana,100");
        assertThrows(IllegalArgumentException.class,
                () -> new TransactionParserImpl().parseTransactions(transactionList));
    }

    @Test
    void parseMaxedIntegerQuantity_notOk() {
        List<String> transactionsList = List.of("b,banana,9999999999");
        assertThrows(ArithmeticException.class,
                () -> new TransactionParserImpl().parseTransactions(transactionsList));
    }

    @Test
    void parseEmptyTransactionList_ok() {
        List<String> transactionsList = List.of();
        List<FruitTransaction> expected = List.of();
        List<FruitTransaction> actual =
                new TransactionParserImpl().parseTransactions(transactionsList);
        assertEquals(expected, actual);
    }

    @Test
    void parseNullTransactionList_notOk() {
        assertThrows(NullPointerException.class,
                () -> new TransactionParserImpl().parseTransactions(null));
    }
}
