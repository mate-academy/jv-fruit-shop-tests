package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConverterImplTest {
    private Converter converter;

    @BeforeEach
    void setUp() {
        converter = new ConverterImpl();
    }

    @Test
    void convert_validData_ok() {
        List<String> inputFileData = List.of(
                "operation,fruit,quantity",
                "b,banana,20",
                "s,apple,100"
        );

        List<FruitTransaction> transactions = converter.convert(inputFileData);

        assertEquals(2, transactions.size());

        FruitTransaction firstTransaction = transactions.get(0);
        assertEquals(FruitTransaction.Operation.BALANCE, firstTransaction.getOperation());
        assertEquals("banana", firstTransaction.getFruit());
        assertEquals(20, firstTransaction.getQuantity());

        FruitTransaction secondTransaction = transactions.get(1);
        assertEquals(FruitTransaction.Operation.SUPPLY, secondTransaction.getOperation());
        assertEquals("apple", secondTransaction.getFruit());
        assertEquals(100, secondTransaction.getQuantity());

    }

    @Test
    void convert_nullInputList_ThrowException() {
        assertThrows(RuntimeException.class, () -> {
            converter.convert(null);
        });
    }

    @Test
    void convert_negativeQuantity_throwException() {
        List<String> inputFileData = List.of(
                "operation,fruit,quantity",
                "b,banana,-10"
        );

        assertThrows(RuntimeException.class, () ->
                converter.convert(inputFileData));
    }

}
