package core.basesyntax.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.FruitTransaction;
import core.basesyntax.FruitTransaction.Operation;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class DataConverterImplTest {
    private DataConverterImpl converter;

    @Before
    public void setUp() {
        converter = new DataConverterImpl();
    }

    @Test
    public void convertToTransaction_validInput_returnsTransactions() {
        List<String> input = Arrays.asList(
                "type,fruit,quantity",
                "b,banana,20",
                "s,apple,50"
        );

        List<FruitTransaction> result = converter.convertToTransaction(input);

        assertEquals(2, result.size());

        FruitTransaction first = result.get(0);
        assertEquals(Operation.BALANCE, first.getOperation());
        assertEquals("banana", first.getFruit());
        assertEquals(20, first.getQuantity());

        FruitTransaction second = result.get(1);
        assertEquals(Operation.SUPPLY, second.getOperation());
        assertEquals("apple", second.getFruit());
        assertEquals(50, second.getQuantity());
    }

    @Test
    public void convertToTransaction_emptyInput_returnsEmptyList() {
        List<String> input = Collections.emptyList();

        List<FruitTransaction> result = converter.convertToTransaction(input);

        assertTrue(result.isEmpty());
    }

    @Test(expected = RuntimeException.class)
    public void convertToTransaction_nullInput_throwsException() {
        converter.convertToTransaction(null);
    }

    @Test
    public void convertToTransaction_ignoresInvalidHeaderRow() {
        List<String> input = Collections.singletonList("type,fruit,quantity");

        List<FruitTransaction> result = converter.convertToTransaction(input);

        assertTrue(result.isEmpty());
    }

    @Test(expected = RuntimeException.class)
    public void convertToTransaction_invalidOperation_throwsException() {
        List<String> input = Arrays.asList(
                "x,banana,10"
        );

        converter.convertToTransaction(input);
    }

    @Test(expected = NumberFormatException.class)
    public void convertToTransaction_nonNumericQuantity_throwsException() {
        List<String> input = Arrays.asList(
                "b,banana,ten"
        );

        converter.convertToTransaction(input);
    }
}
