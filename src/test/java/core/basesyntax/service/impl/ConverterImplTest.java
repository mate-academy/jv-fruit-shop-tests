package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConverterImplTest {

    private final ConverterImpl converter = new ConverterImpl();

    @Test
    public void allDataIsCorrect() {
        List<String> data = Arrays.asList("b,banana,20", "s,apple,5");
        List<FruitTransaction> resultData = converter.convert(data);
        assertEquals(2, resultData.size());

        FruitTransaction fruitTransaction_1 = resultData.get(0);
        assertEquals(Operation.BALANCE, fruitTransaction_1.getOperation());
        assertEquals("banana", fruitTransaction_1.getFruit());
        assertEquals(20, fruitTransaction_1.getQuantity());

        FruitTransaction fruitTransaction_2 = resultData.get(0);
        assertEquals(Operation.SUPPLY, fruitTransaction_2.getOperation());
        assertEquals("apple", fruitTransaction_2.getFruit());
        assertEquals(5, fruitTransaction_2.getQuantity());
    }

    @Test
    public void ignoreEmptySpaces() {
        List<String> data = Arrays.asList("  s, apple , 5  ", "", "b, banana,10");
        List<FruitTransaction> resultData = converter.convert(data);
        assertEquals(2, resultData.size());
    }

    @Test
    public void wrongNumberOfData() {
        List<String> data = Arrays.asList("b,20");
        RuntimeException exception = assertThrows(RuntimeException.class, () -> converter.convert(data));
        String message = exception.getMessage();
        assertTrue(message.contains("Wrong format of the line"));
        assertTrue(message.contains("2"));
    }

    @Test
    public void unknownOperationType() {
        List<String> data = Arrays.asList("x, banana, 20");
        RuntimeException exception = assertThrows(RuntimeException.class, () -> converter.convert(data));
        String message = exception.getMessage();

        assertTrue(message.contains("Invalid operation symbol in line"));
        assertTrue(message.contains("1"));
    }

    @Test
    public void badQuantityFormat() {
        List<String> data = Arrays.asList("b, banana, b");
        RuntimeException exception = assertThrows(RuntimeException.class, () -> converter.convert(data));
        String message = exception.getMessage();

        assertTrue(message.contains("Incorrect number in a line"));
        assertTrue(message.contains("1"));
    }

    @Test
    public void negativeQuantity() {
        List<String> data = Arrays.asList("b, banana, -5");
        RuntimeException exception = assertThrows(RuntimeException.class, () -> converter.convert(data));
        String message = exception.getMessage();
        assertTrue(message.contains("Quantity can't be negative"));
        assertTrue(message.contains("1"));
    }

    @Test
    public void emptyEntryList() {
        List<String> data = Collections.emptyList();
        List<FruitTransaction> resultData = converter.convert(data);
        assertNotNull(resultData);
        assertEquals(0, resultData.size());
    }
}