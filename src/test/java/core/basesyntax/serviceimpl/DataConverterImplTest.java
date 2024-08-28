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
    private static final List<FruitTransaction> EXPECTED_CONVERTER_RESULTS = List.of(
            new FruitTransaction(FruitTransaction.Operation.BALANCE,
                    "banana",
                    20),
            new FruitTransaction(FruitTransaction.Operation.BALANCE,
                    "apple",
                    100),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                    "banana",
                    100),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                    "banana",
                    13),
            new FruitTransaction(FruitTransaction.Operation.RETURN,
                    "apple",
                    10),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                    "apple",
                    20),
            new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                    "banana",
                    5),
            new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                    "banana",
                    50)
    );
    private static final List<String> EMPTY_INPUT = List.of();
    private static DataConverterImpl dataConverter;

    @BeforeAll
    static void beforeAll() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convert_validInput_ok() {
        List<FruitTransaction> actualConverterResults = dataConverter.convert(FILE_DATA);
        assertEquals(FILE_DATA.size(), actualConverterResults.size());
        for (int i = 0; i < actualConverterResults.size(); i++) {
            FruitTransaction expected = EXPECTED_CONVERTER_RESULTS.get(i);
            FruitTransaction actual = actualConverterResults.get(i);
            assertEquals(expected.getOperation(), actual.getOperation());
            assertEquals(expected.getQuantity(), actual.getQuantity());
            assertEquals(expected.getFruit(), actual.getFruit());
        }
    }

    @Test
    void convert_emptyInput_notOk() {
        List<FruitTransaction> actual = dataConverter.convert(EMPTY_INPUT);
        assertEquals(EMPTY_INPUT.size(), actual.size());
        assertTrue(actual.isEmpty());
    }
}
