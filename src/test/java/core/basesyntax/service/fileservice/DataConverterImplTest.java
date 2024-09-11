package core.basesyntax.service.fileservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.UnsupportedOperationException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static final String NAME_WITH_NUMBER = "b,0range,15";
    private static final String NAME_ONLY_NUMBER = "b,2412,2";
    private static final String NEGATIVE_QUANTITY = "b,banana,-30";
    private static final String UNSUPPORTED_OPERATION = "e,orange,30";

    private static final List<String> TEST_INPUT_DATA = List.of(
            "type,fruit,quantity",
            "b,banana,15",
            "p,apple,20",
            "r,lemon,25",
            "s,orange,30"
    );

    private static final Operation[] EXPECTED_OPERATIONS = {
            Operation.BALANCE,
            Operation.PURCHASE,
            Operation.RETURN,
            Operation.SUPPLY
    };

    private static final String[] EXPECTED_FRUITS = {
            "banana",
            "apple",
            "lemon",
            "orange"
    };

    private static final int[] EXPECTED_QUANTITY = {
            15,20,25,30
    };

    private DataConverter dataConverter;
    private List<String> testInputData;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
        testInputData = new ArrayList<>();
        testInputData.addAll(TEST_INPUT_DATA);
    }

    @Test
    void covertValidData_ok() {
        List<FruitTransaction> actual = dataConverter.convertToTransaction(testInputData);
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(EXPECTED_OPERATIONS[i], actual.get(i).getOperation());
            assertEquals(EXPECTED_FRUITS[i], actual.get(i).getFruit());
            assertEquals(EXPECTED_QUANTITY[i], actual.get(i).getQuantity());
        }
    }

    @Test
    void convertDataNegativeQuantity_notOk() {
        testInputData.add(NEGATIVE_QUANTITY);
        assertThrows(IllegalArgumentException.class, ()
                -> dataConverter.convertToTransaction(testInputData));
    }

    @Test
    void convertDataInvalidOperation_notOk() {
        testInputData.add(UNSUPPORTED_OPERATION);
        assertThrows(UnsupportedOperationException.class, ()
                -> dataConverter.convertToTransaction(testInputData));
    }

    @Test
    void convertDataFruitNameContainsNumbers_notOk() {
        testInputData.add(NAME_WITH_NUMBER);
        assertThrows(IllegalArgumentException.class, ()
                -> dataConverter.convertToTransaction(testInputData));
    }

    @Test
    void convertDataFruitsContainsOnlyNumbers_notOk() {
        testInputData.add(NAME_ONLY_NUMBER);
        assertThrows(IllegalArgumentException.class, () ->
                dataConverter.convertToTransaction(testInputData));
    }
}
