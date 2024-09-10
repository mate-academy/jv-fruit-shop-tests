package core.basesyntax.service.fileservice;

import core.basesyntax.exception.UnsupportedOperationException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataConverterImplTest {
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
        testInputData.add("type,fruit,quantity");
        testInputData.add("b,banana,15");
        testInputData.add("p,apple,20");
        testInputData.add("r,lemon,25");
        testInputData.add("s,orange,30");
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
        testInputData.add("b,banana,-30");
        assertThrows(IllegalArgumentException.class, ()
                -> dataConverter.convertToTransaction(testInputData));
    }

    @Test
    void convertDataInvalidOperation_notOk() {
        testInputData.add("e,orange,30");
        assertThrows(UnsupportedOperationException.class, ()
                -> dataConverter.convertToTransaction(testInputData));
    }

}