package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class TransactionParserImplTest {
    private static final TransactionParserImpl transactionParser
            = new TransactionParserImpl();

    private static final FruitTransaction VALID_TRANSACTION1
            = new FruitTransaction(
            FruitTransaction.Operation.BALANCE, "apple", 100);
    private static final FruitTransaction VALID_TRANSACTION2
            = new FruitTransaction(
            FruitTransaction.Operation.SUPPLY, "banana", 20);
    private static final List<FruitTransaction> fruitTransactionList
            = new ArrayList<>();
    private static final String VALID_STRING1 = "b,apple,100";
    private static final String VALID_STRING2 = "s,banana,20";
    private static final String NOT_VALID_STRING = "banana,20";
    private static final String NOT_VALID_OPERATION_STRING = "abc,banana,20";
    private static final List<String> VALID_STRING_LIST = new ArrayList<>();

    @Test
    void parseTransactions_ValidData_Ok() {
        fruitTransactionList.add(VALID_TRANSACTION1);
        fruitTransactionList.add(VALID_TRANSACTION2);
        VALID_STRING_LIST.add(VALID_STRING1);
        VALID_STRING_LIST.add(VALID_STRING2);
        assertEquals(fruitTransactionList, transactionParser
                .parseTransactions(VALID_STRING_LIST));
    }

    @Test
    void parseTransactions_NotValidData_notOk() {
        assertThrows(IllegalArgumentException.class, () -> transactionParser
                .parseTransactions(List.of(NOT_VALID_STRING)));
    }

    @Test
    void parseTransactions_NotValidOperation_notOk() {
        assertThrows(RuntimeException.class, () -> transactionParser
                .parseTransactions(List.of(NOT_VALID_OPERATION_STRING)));
    }
}
