package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.exception.FruitShopException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransactionList_ok() {
        List<String> input = List.of(
                "b,banana,100",
                "s,apple,50",
                "p,orange,20"
        );

        List<FruitTransaction> transactions = dataConverter.convertToTransaction(input);

        List<FruitTransaction> expected = List.of(
                new FruitTransaction(Operation.BALANCE, "banana", 100),
                new FruitTransaction(Operation.SUPPLY, "apple", 50),
                new FruitTransaction(Operation.PURCHASE, "orange", 20)
        );

        assertEquals(expected, transactions);
    }

    @Test
    void convertToTransactionList_nullInput_notOk() {
        assertThrows(FruitShopException.class, () -> dataConverter.convertToTransaction(null));
    }

    @Test
    void convertToTransactionList_invalidFormat_notOk() {
        List<String> input = List.of("invalid_line_without_commas");
        assertThrows(FruitShopException.class, () -> dataConverter.convertToTransaction(input));
    }
}
