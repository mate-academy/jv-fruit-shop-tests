package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final int BANANA_QUANTITY = 20;
    private static final int APPLE_QUANTITY = 10;

    private DataConverter converter;

    @BeforeEach
    void setUp() {
        converter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_validInput_ok() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "s,apple,10"
        );
        List<FruitTransaction> result = converter.convertToTransaction(input);
        assertEquals(2, result.size());
        FruitTransaction firstTransaction = result.get(0);
        assertEquals(FruitTransaction.Operation.BALANCE, firstTransaction.getOperation());
        assertEquals(BANANA, firstTransaction.getFruit());
        assertEquals(BANANA_QUANTITY, firstTransaction.getQuantity());
        FruitTransaction secondTransaction = result.get(1);
        assertEquals(FruitTransaction.Operation.SUPPLY, secondTransaction.getOperation());
        assertEquals(APPLE, secondTransaction.getFruit());
        assertEquals(APPLE_QUANTITY, secondTransaction.getQuantity());
    }

    @Test
    void convertToTransaction_unValidInput_notOk() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "x,banana,20"
        );
        assertThrows(IllegalArgumentException.class, () -> {
            converter.convertToTransaction(input);
        });
    }
}
