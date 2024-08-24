package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DataConverterImplTest {
    private static final List<String> FILE_DATA = List.of("b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50");
    private static final FruitTransaction FIRST_TRANSACTION = new FruitTransaction(
            FruitTransaction.Operation.BALANCE,
            "banana",
            20);
    private static final int FIRST_TRANSACTION_INDEX = 0;
    private static final List<String> EMPTY_INPUT = List.of();
    private static DataConverterImpl dataConverter;

    @BeforeAll
    static void beforeAll() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convert_validInput_Ok() {
        List<FruitTransaction> actual = dataConverter.convert(FILE_DATA);
        assertEquals(FILE_DATA.size(), actual.size());
        assertEquals(FIRST_TRANSACTION.getFruit(),
                actual.get(FIRST_TRANSACTION_INDEX).getFruit());
        assertEquals(FIRST_TRANSACTION.getQuantity(),
                actual.get(FIRST_TRANSACTION_INDEX).getQuantity());
        assertEquals(FIRST_TRANSACTION.getOperation(),
                actual.get(FIRST_TRANSACTION_INDEX).getOperation());
    }

    @Test
    void convert_emptyInput_NotOk() {
        List<FruitTransaction> actual = dataConverter.convert(EMPTY_INPUT);
        assertEquals(EMPTY_INPUT.size(), actual.size());
        assertTrue(actual.isEmpty());
    }
}
