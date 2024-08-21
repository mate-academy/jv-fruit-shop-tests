package core.basesyntax.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Transaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StringTransactionConverterImplTest {
    private List<String> stringTransactions;
    private final StringTransactionConverter stringTransactionConverter =
            new StringTransactionConverterImpl();

    @BeforeEach
    void init() {
        stringTransactions = new ArrayList<>();
        stringTransactions.add("type,fruit,quantity");
    }

    @Test
    void convert_NullList_ThrowsException() {
        assertThrows(RuntimeException.class, () -> stringTransactionConverter.convert(null));
    }

    @Test
    void invalidTransactionRecordTypeOfTransaction() {
        stringTransactions.add("1,apple,10");
        assertThrows(RuntimeException.class, () ->
                stringTransactionConverter.convert(stringTransactions));
    }

    @Test
    void invalidTransactionRecordNoTransactionAmount() {
        stringTransactions.add("2,banana");
        assertThrows(RuntimeException.class, () ->
                stringTransactionConverter.convert(stringTransactions));
    }

    @Test
    void invalidTransactionRecordNoTransactionAmountNegative() {
        stringTransactions.add("b,apple,-200");
        stringTransactions.add("b,banana,100");
        assertThrows(RuntimeException.class, () ->
                stringTransactionConverter.convert(stringTransactions));
    }

    @Test
    void convert_ValidInput_ReturnsListOfTransactions() {
        stringTransactions.add("b,apple,50");
        stringTransactions.add("b,banana,50");
        stringTransactions.add("p,apple,45");
        stringTransactions.add("p,banana,45");
        stringTransactions.add("s,apple,20");
        stringTransactions.add("s,banana,20");
        stringTransactions.add("r,apple,15");
        stringTransactions.add("r,banana,15");

        List<Transaction> transactionList;
        transactionList = stringTransactionConverter
                .convert(stringTransactions);

        assertEquals(8, transactionList.size());

        Transaction firstTransaction = transactionList.get(0);
        assertEquals(Transaction.TransactionType.getTransactionTypeByCode("b"),
                firstTransaction.getTransactionType());
        assertEquals("apple", firstTransaction.getFruit().getFruitName());
        assertEquals(50, firstTransaction.getAmount());

        Transaction secondTransaction = transactionList.get(1);
        assertEquals(Transaction.TransactionType.getTransactionTypeByCode("b"),
                secondTransaction.getTransactionType());
        assertEquals("banana", secondTransaction.getFruit().getFruitName());
        assertEquals(50, secondTransaction.getAmount());

        Transaction thirdTransaction = transactionList.get(2);
        assertEquals(Transaction.TransactionType.getTransactionTypeByCode("p"),
                thirdTransaction.getTransactionType());
        assertEquals("apple", thirdTransaction.getFruit().getFruitName());
        assertEquals(45, thirdTransaction.getAmount());

        Transaction fourthTransaction = transactionList.get(3);
        assertEquals(Transaction.TransactionType.getTransactionTypeByCode("p"),
                fourthTransaction.getTransactionType());
        assertEquals("banana", fourthTransaction.getFruit().getFruitName());
        assertEquals(45, fourthTransaction.getAmount());

        Transaction fifthTransaction = transactionList.get(4);
        assertEquals(Transaction.TransactionType.getTransactionTypeByCode("s"),
                fifthTransaction.getTransactionType());
        assertEquals("apple", fifthTransaction.getFruit().getFruitName());
        assertEquals(20, fifthTransaction.getAmount());

        Transaction sixthTransaction = transactionList.get(5);
        assertEquals(Transaction.TransactionType.getTransactionTypeByCode("s"),
                sixthTransaction.getTransactionType());
        assertEquals("banana", sixthTransaction.getFruit().getFruitName());
        assertEquals(20, sixthTransaction.getAmount());

        Transaction seventhTransaction = transactionList.get(6);
        assertEquals(Transaction.TransactionType.getTransactionTypeByCode("r"),
                seventhTransaction.getTransactionType());
        assertEquals("apple", seventhTransaction.getFruit().getFruitName());
        assertEquals(15, seventhTransaction.getAmount());

        Transaction eigthTransaction = transactionList.get(7);
        assertEquals(Transaction.TransactionType.getTransactionTypeByCode("r"),
                eigthTransaction.getTransactionType());
        assertEquals("banana", eigthTransaction.getFruit().getFruitName());
        assertEquals(15, eigthTransaction.getAmount());
    }

    @Test
    void noSuchTransactionType() {
        stringTransactions.add(0, "k,banana,manana");
        assertThrows(RuntimeException.class, () -> {
            stringTransactionConverter.convert(stringTransactions);
        });
    }

    @Test
    void noSuchTransactionTypeInteger() {
        stringTransactions.add(0, "1,banana,manana");
        assertThrows(RuntimeException.class, () -> {
            stringTransactionConverter.convert(stringTransactions);
        });
    }

    @Test
    void noComaInCsvecords() {
        stringTransactions.add(0, "!!!!!");
        assertThrows(RuntimeException.class, () -> {
            stringTransactionConverter.convert(stringTransactions);
        });
    }

    @Test
    void onlyComasInCsvFileTransactionRecord() {
        stringTransactions.add(0, ",,,,,,");
        assertThrows(RuntimeException.class, () -> {
            stringTransactionConverter.convert(stringTransactions);
        });
    }

    @Test
    void noTransactionTypeIndicated() {
        stringTransactions.add(0, ",banana,20, 50, 888,iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii;;;");
        assertThrows(RuntimeException.class, () -> {
            stringTransactionConverter.convert(stringTransactions);
        });
    }
}
