package core.basesyntax.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import java.util.function.Function;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private DataConverter converter;
    private Function<String, FruitTransaction> mapper;

    @BeforeEach
    void setUp() {
        mapper = new TransactionParser();
        converter = new DataConverterImpl(mapper);
    }

    @Test
    void convertToTransaction_validTransactionList_ok() {
        List<String> inputTransactionList = List.of("b,banana,100", "p,apple,50", "s,banana,50");
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 50),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50));
        List<FruitTransaction> actual = converter.convertToTransaction(inputTransactionList);
        assertEquals(expected, actual);
    }

    @Test
    void convertToTransaction_nullInputTransactionList_notOk() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(null));
        assertEquals("Input list can't be null", exception.getMessage());
    }

    @Test
    void convertToTransaction_emptyList_ok() {
        List<String> inputTransaction = List.of();
        List<FruitTransaction> actual = converter.convertToTransaction(inputTransaction);
        assertTrue(actual.isEmpty());
    }
}
