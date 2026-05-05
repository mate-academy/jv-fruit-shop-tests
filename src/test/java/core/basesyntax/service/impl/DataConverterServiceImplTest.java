package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.Test;

public class DataConverterServiceImplTest {
    private final DataConverterServiceImpl converter = new DataConverterServiceImpl();

    @Test
    void convert_validInput_returnsCorrectTransactions() {
        List<String> input = List.of(
                "b,banana,200",
                "s,apple,500",
                "p,orange,700",
                "r,cucumber,900");
        List<FruitTransaction> result = converter.convertToTransaction(input);

        FruitTransaction first = result.get(0);
        assertEquals(FruitTransaction.Operation.BALANCE, first.getOperation());
        assertEquals("banana", first.getFruit());
        assertEquals(200, first.getQuantity());

        FruitTransaction second = result.get(1);
        assertEquals(FruitTransaction.Operation.SUPPLY, second.getOperation());
        assertEquals("apple", second.getFruit());
        assertEquals(500, second.getQuantity());

        FruitTransaction third = result.get(2);
        assertEquals(FruitTransaction.Operation.PURCHASE, third.getOperation());
        assertEquals("orange", third.getFruit());
        assertEquals(700, third.getQuantity());

        FruitTransaction fourth = result.get(3);
        assertEquals(FruitTransaction.Operation.RETURN, fourth.getOperation());
        assertEquals("cucumber", fourth.getFruit());
        assertEquals(900, fourth.getQuantity());
    }

    @Test
    void convert_emptyInput_returnsEmptyList() {
        List<FruitTransaction> result = converter.convertToTransaction(List.of());
        assertTrue(result.isEmpty());
    }

    @Test
    void convert_nullInput_returnsEmptyList() {
        List<FruitTransaction> result = converter.convertToTransaction(null);
        assertTrue(result.isEmpty());
    }

    @Test
    void convert_invalidFormat_returnsEmptyList() {
        List<String> input = List.of(
                "b,banana,200,200",
                "s,apple");
        List<FruitTransaction> result = converter.convertToTransaction(input);
        assertTrue(result.isEmpty());
    }

    @Test
    void convert_emptyLines_returnsEmptyList() {
        List<String> input = List.of(
                "",
                "", "");
        List<FruitTransaction> result = converter.convertToTransaction(input);
        assertTrue(result.isEmpty());
    }

    @Test
    void convert_invalidOperation_throwsException() {
        List<String> input = List.of(
                "x,banana,200");
        assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(input));
    }

    @Test
    void convert_partiallyInvalidInput_returnsOnlyValidTransactions() {
        List<String> input = List.of(
                "b,banana,200",
                "s,apple,505",
                "s,200");
        List<FruitTransaction> result = converter.convertToTransaction(input);
        assertEquals(2, result.size());
    }

    @Test
    void convert_invalidNumber_throwsException() {
        List<String> input = List.of("b,banana,abc");
        assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(input));
    }
}
