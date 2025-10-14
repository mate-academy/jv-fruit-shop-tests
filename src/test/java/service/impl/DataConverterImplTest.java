package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import model.FruitTransaction;
import model.FruitTransaction.Operation;
import org.junit.jupiter.api.Test;
import service.DataConverter;

public class DataConverterImplTest {

    private final DataConverter dataConverter = new DataConverterImpl();

    @Test
    void convertToTransactions_validData_returnsListOfTransactions() {
        List<String> lines = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "s,apple,15",
                "p,banana,5"
        );

        List<FruitTransaction> actual = dataConverter.convertToTransactions(lines);

        List<FruitTransaction> expected = getExpectedTransactions();
        assertEquals(expected, actual, "Converted transactions should match expected list");
    }

    @Test
    void convertToTransactions_emptyList_returnsEmptyList() {
        List<String> lines = new ArrayList<>();

        List<FruitTransaction> result = dataConverter.convertToTransactions(lines);

        assertTrue(result.isEmpty(), "Expected empty list for empty input");
    }

    @Test
    void convertToTransactions_invalidOperation_throwsException() {
        List<String> lines = List.of(
                "type,fruit,quantity",
                "x,apple,10"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> dataConverter.convertToTransactions(lines),
                "Expected exception for invalid operation code"
        );
    }

    @Test
    void convertToTransactions_invalidQuantity_throwsException() {
        List<String> lines = List.of(
                "type,fruit,quantity",
                "s,apple,abc"
        );

        assertThrows(
                NumberFormatException.class,
                () -> dataConverter.convertToTransactions(lines),
                "Expected exception for non-numeric quantity"
        );
    }

    private List<FruitTransaction> getExpectedTransactions() {
        return List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 20),
                new FruitTransaction(Operation.SUPPLY, "apple", 15),
                new FruitTransaction(Operation.PURCHASE, "banana", 5)
        );
    }
}

