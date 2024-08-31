package core.basesyntax.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.Transaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StringToTransactionConverterImplTest {
    private List<String> stringTransactions;
    private final StringTransactionConverter stringTransactionConverter =
            new StringToTransactionConverterImpl();

    @BeforeEach
    void init() {
        stringTransactions = new ArrayList<>();
        stringTransactions.add("type,fruit,quantity");
        stringTransactions.add("b,apple,50");
        stringTransactions.add("b,banana,50");
        stringTransactions.add("p,apple,45");
        stringTransactions.add("p,banana,45");
        stringTransactions.add("s,apple,20");
        stringTransactions.add("s,banana,20");
        stringTransactions.add("r,apple,15");
        stringTransactions.add("r,banana,15");
    }

    @Test
    void convertStringRecordToTransactionNullListThrowsException() {
        assertThrows(RuntimeException.class, () ->
                stringTransactionConverter.convertStringRecordToTransaction(null));
    }

    @Test
    void invalidTransactionRecordTypeOfTransaction() {
        stringTransactions.add("1,apple,10");
        assertThrows(RuntimeException.class, () ->
                stringTransactionConverter.convertStringRecordToTransaction(stringTransactions));
    }

    @Test
    void invalidTransactionRecordNoTransactionAmount() {
        stringTransactions.add("2,banana");
        assertThrows(RuntimeException.class, () ->
                stringTransactionConverter.convertStringRecordToTransaction(stringTransactions));
    }

    @Test
    void invalidTransactionRecordNoTransactionAmountNegative() {
        stringTransactions.add("b,apple,-200");
        stringTransactions.add("b,banana,100");
        assertThrows(RuntimeException.class, () ->
                stringTransactionConverter.convertStringRecordToTransaction(stringTransactions));
    }

    @Test
    void convertStringRecordToTransactionValidInputReturnsListOfTransactions() {
        List<Transaction> transactionList;
        transactionList = stringTransactionConverter
                .convertStringRecordToTransaction(stringTransactions);
        assertEquals(8, transactionList.size());
    }

    @Test
    void noSuchTransactionType() {
        stringTransactions.add(0, "k,banana,manana");
        assertThrows(RuntimeException.class, () -> {
            stringTransactionConverter.convertStringRecordToTransaction(stringTransactions);
        });
    }

    @Test
    void noSuchTransactionTypeInteger() {
        stringTransactions.add(0, "1,banana,manana");
        assertThrows(RuntimeException.class, () -> {
            stringTransactionConverter.convertStringRecordToTransaction(stringTransactions);
        });
    }

    @Test
    void noComaInCsvRecords() {
        stringTransactions.add(0, "!!!!!");
        assertThrows(RuntimeException.class, () -> {
            stringTransactionConverter.convertStringRecordToTransaction(stringTransactions);
        });
    }

    @Test
    void onlyComasInCsvFileTransactionRecord() {
        stringTransactions.add(0, ",,,,,,");
        assertThrows(RuntimeException.class, () -> {
            stringTransactionConverter.convertStringRecordToTransaction(stringTransactions);
        });
    }

    @Test
    void noTransactionTypeIndicated() {
        stringTransactions.add(0, ",banana,20, 50, 888,iiiiiiiiiiiiiii;;;");
        assertThrows(RuntimeException.class, () -> {
            stringTransactionConverter.convertStringRecordToTransaction(stringTransactions);
        });
    }
}
