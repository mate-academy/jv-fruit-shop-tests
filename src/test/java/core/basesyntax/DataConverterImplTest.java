package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntex.model.FruitTransaction;
import core.basesyntex.model.Operation;
import core.basesyntex.service.DataConverter;
import core.basesyntex.service.impl.DataConverterGetter;
import java.util.List;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    private final DataConverter dataConverter = DataConverterGetter.getDataConverter();

    @Test
    void convertToTransaction_valid_input_OK() {
        List<String> lines = List.of(
                "b,apple,50",
                "p,banana,-20",
                "s,orange,30");
        List<FruitTransaction> result = dataConverter.convertToTransaction(lines);
        assertEquals(3,result.size());

        FruitTransaction transaction1 = result.get(0);
        assertEquals(Operation.BALANCE, transaction1.getOperation());
        assertEquals("apple", transaction1.getFruit());
        assertEquals(50, transaction1.getQuantity());

        FruitTransaction transaction2 = result.get(1);
        assertEquals(Operation.PURCHASE, transaction2.getOperation());
        assertEquals("banana", transaction2.getFruit());
        assertEquals(-20, transaction2.getQuantity());

        FruitTransaction transaction3 = result.get(2);
        assertEquals(Operation.SUPPLY, transaction3.getOperation());
        assertEquals("orange", transaction3.getFruit());
        assertEquals(30, transaction3.getQuantity());
    }

    @Test
    void convertToTransactions_InvalidInputLength_notOK() {
        List<String> lines = List.of(
                "b,apple",
                "p, banana,-20, test");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convertToTransaction(lines);
        });
        assertTrue(exception.getMessage().contains("Invalid input line"));
    }

    @Test
    void convertToTransaction_invalidOperationCode_notOk() {
        List<String> lines = List.of(
                "test,apple,50");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convertToTransaction(lines);
        });
        assertTrue(exception.getMessage().contains("Unknown operation code"));
    }

    @Test
    void convertToTransaction_invalidQuantity_notOk() {
        List<String> lines = List.of(
                "b,apple,test"
        );
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            dataConverter.convertToTransaction(lines);
        });
        assertTrue(exception.getMessage().contains("Invalid quantity"));
    }
}
