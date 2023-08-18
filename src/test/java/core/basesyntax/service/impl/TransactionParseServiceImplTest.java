package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionParseService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionParseServiceImplTest {
    private static TransactionParseService transactionParseService;

    @BeforeAll
    static void beforeAll() {
        transactionParseService = new TransactionParseServiceImpl();
    }

    @Test
    void transactionParseEmptyList_notOk() {
        assertThrows(RuntimeException.class,
                () -> transactionParseService.getTransactionData(new ArrayList<>()));
    }

    @Test
    void transactionParseNullList_notOk() {
        assertThrows(RuntimeException.class,
                () -> transactionParseService.getTransactionData(null));
    }

    @Test
    void transactionParseValidData_oK() {
        List<String> data = List.of("type,fruit,quantity", "b,banana,20",
                "s,apple,100", "p,banana,100");
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 100));
        List<FruitTransaction> actual = transactionParseService.getTransactionData(data);
        assertEquals(expected, actual);
    }
}
