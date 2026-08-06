package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private DataConverterImpl dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_validInput_skipsHeader() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,100",
                "s,apple,50",
                "p,ananas,20");

        List<FruitTransaction> actual = dataConverter.convertToTransaction(input);
        assertEquals(3, actual.size());

        FruitTransaction firstTransaction = actual.get(0);
        assertEquals(FruitTransaction.Operation.BALANCE,
                firstTransaction.getOperation());
        assertEquals("banana", firstTransaction.getFruit());
        assertEquals(100, firstTransaction.getQuantity());
    }

    @Test
    void convertToTransaction_onlyHeader_returnsEmptyList() {
        List<String> input = List.of("type,fruit,quantity");

        List<FruitTransaction> transactionList = dataConverter.convertToTransaction(input);
        assertEquals(0, transactionList.size());
    }

    @Test
    void convertToTransaction_invalidOperationCode_throwsException() {
        List<String> input = List.of("type,fruit,quantity",
                "x,banana,10");
        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(input));
    }
}
