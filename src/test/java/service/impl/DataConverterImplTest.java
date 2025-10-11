package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import model.FruitTransaction;
import org.junit.jupiter.api.Test;
import service.DataConverter;

public class DataConverterImplTest {

    private final DataConverter dataConverter = new DataConverterImpl();

    @Test
    void convertToTransactions_validData_returnsListOfTransactions() {
        List<String> lines = new ArrayList<>();
        lines.add("type,fruit,quantity");
        lines.add("b,banana,20");
        lines.add("s,apple,15");
        lines.add("p,banana,5");

        List<FruitTransaction> result = dataConverter.convertToTransactions(lines);

        assertEquals(3, result.size());

        FruitTransaction first = result.get(0);
        assertEquals(FruitTransaction.Operation.BALANCE, first.getOperation());
        assertEquals("banana", first.getFruit());
        assertEquals(20, first.getQuantity());

        FruitTransaction second = result.get(1);
        assertEquals(FruitTransaction.Operation.SUPPLY, second.getOperation());
        assertEquals("apple", second.getFruit());
        assertEquals(15, second.getQuantity());

        FruitTransaction third = result.get(2);
        assertEquals(FruitTransaction.Operation.PURCHASE, third.getOperation());
        assertEquals("banana", third.getFruit());
        assertEquals(5, third.getQuantity());
    }

    @Test
    void convertToTransactions_emptyList_returnsEmptyList() {
        List<String> lines = new ArrayList<>();
        List<FruitTransaction> result = dataConverter.convertToTransactions(lines);
        assertTrue(result.isEmpty());
    }

    @Test
    void convertToTransactions_invalidOperation_throwsException() {
        List<String> lines = new ArrayList<>();
        lines.add("type,fruit,quantity");
        lines.add("x,apple,10");

        assertThrows(
                IllegalArgumentException.class,
                () -> dataConverter.convertToTransactions(lines)
        );
    }

    @Test
    void convertToTransactions_invalidQuantity_throwsException() {
        List<String> lines = new ArrayList<>();
        lines.add("type,fruit,quantity");
        lines.add("s,apple,abc");

        assertThrows(
                NumberFormatException.class,
                () -> dataConverter.convertToTransactions(lines)
        );
    }
}
