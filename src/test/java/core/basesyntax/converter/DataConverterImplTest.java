package core.basesyntax.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    private DataConverterImpl dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransactionsList_validInput_ok() {
        List<FruitTransaction> expectedList = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 30));

        List<String> validInput = List.of(
                new String("type,fruit,quantity"),
                new String("b,banana,100"),
                new String("b,apple,100"),
                new String("p,banana,20"),
                new String("p,apple,30"));

        List<FruitTransaction> actualList = dataConverter.convertToTransactionsList(validInput);

        assertEquals(expectedList, actualList);
    }

    @Test
    void convertToTransactionsList_invalidInput_throwsRuntimeExceptions() {
        List<String> invalidInput = List.of(
                new String("type,fruit,quantity"));

        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransactionsList(invalidInput));
    }

    @Test
    void convertToTransactionsList_nullInput_throwsRuntimeExceptions() {
        assertThrows(RuntimeException.class, () -> dataConverter.convertToTransactionsList(null));
    }

    @Test
    void convertToTransaction_validInput_ok() {
        String input = "b,banana,100";
        FruitTransaction transaction = dataConverter.convertToTransaction(input);

        assertEquals(
                FruitTransaction.Operation.BALANCE,
                transaction.getOperation());
        assertEquals("banana", transaction.getFruit());
        assertEquals(100, transaction.getQuantity());
    }

    @Test
    void convertToTransaction_invalidInput_throwsIllegalArgumentException() {
        String input = "banana,100";
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(input));
    }
}
