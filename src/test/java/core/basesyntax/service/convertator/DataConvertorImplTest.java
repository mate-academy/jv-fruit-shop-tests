package core.basesyntax.service.convertator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataConvertorImplTest {
    private static final String CORRECT_DATA_FOR_CONVERTATION = "type,fruit,quantity"
            + System.lineSeparator()
            + "b,banana,20"
            + System.lineSeparator()
            + "b,apple,100"
            + System.lineSeparator()
            + "s,banana,100"
            + System.lineSeparator()
            + "p,banana,13"
            + System.lineSeparator()
            + "r,apple,10"
            + System.lineSeparator()
            + "p,apple,20"
            + System.lineSeparator()
            + "p,banana,5"
            + System.lineSeparator()
            + "s,banana,50";
    private static final String INCORRECT_OPERATION_DATA = "type,fruit,quantity"
            + System.lineSeparator()
            + "q,banana,20";
    private static final String INCORRECT_DATA = "type,fruit,quantity"
            + System.lineSeparator()
            + "q,,20";
    private static DataConvertor dataConvertor;
    private static List<FruitTransaction> correctObjects;

    @BeforeAll
    static void beforeAll() {
        dataConvertor = new DataConvertorImpl();
        correctObjects = getCorrectObjects();
    }

    @Test
    void convertData_isOk() {
        List<FruitTransaction> convertedData =
                dataConvertor.convertData(CORRECT_DATA_FOR_CONVERTATION);
        assertTrue(convertedData.size() == correctObjects.size()
                && convertedData.equals(correctObjects));

    }

    @Test
    void convertDataWithIncorrectOperation_expectedException() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            dataConvertor.convertData(INCORRECT_OPERATION_DATA);
        });
        String expectedMassage = "Invalid operation code: " + "q";
        String actualMassage = exception.getMessage();
        assertEquals(expectedMassage, actualMassage);
    }

    @Test
    void convertIncorrectData_expectedException() {
        assertThrows(IllegalArgumentException.class, () -> {
            dataConvertor.convertData(INCORRECT_DATA);
        });
    }

    private static List<FruitTransaction> getCorrectObjects() {
        return dataConvertor.convertData(CORRECT_DATA_FOR_CONVERTATION);
    }
}
