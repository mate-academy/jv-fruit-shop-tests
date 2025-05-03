package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.errors.ErrorMessages;
import core.basesyntax.model.Fruit;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testing ReportGeneratorImpl")
class ReportGeneratorImplTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int QUANTITY_5 = 5;
    private static final int QUANTITY_10 = 10;
    private static final String HEAD = "fruit,quantity";
    private static final String DATA_DELIMITER = ",";
    private ReportGeneratorImpl reportGenerator;

    @BeforeEach
    void setUp() {
        FruitDao fruitDao = new FruitDaoImpl();
        reportGenerator = new ReportGeneratorImpl(fruitDao);
    }

    @Test
    void getReport_validData_ok() {
        Map<String, Integer> testDataFruits = Map.of(APPLE, QUANTITY_5, BANANA, QUANTITY_10);
        fillStorageWithTestData(testDataFruits);
        String expected = getExpectedData(testDataFruits);
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }

    @Test
    void getReport_emptyData_ok() {
        Map<String, Integer> testDataFruits = Collections.emptyMap();
        fillStorageWithTestData(testDataFruits);
        String expected = getExpectedData(testDataFruits);
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }

    @Test
    void getReport_nullData_notOk() {
        Storage.addFruit(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                reportGenerator.getReport());
        String expectedMessage = ErrorMessages.FRUIT_CANNOT_BE_NULL_OR_EMPTY;
        assertEquals(expectedMessage, exception.getMessage(),
                getInfoMessage(expectedMessage, exception.getMessage())
        );
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }

    private void fillStorageWithTestData(Map<String, Integer> dataFruits) {
        dataFruits.forEach((key, value) -> {
            Fruit fruit = new Fruit(key);
            fruit.setQuantity(value);
            Storage.addFruit(fruit);
        });
    }

    private String getExpectedData(Map<String, Integer> dataFruits) {
        return HEAD + System.lineSeparator() + dataFruits.entrySet().stream()
                .map(dataFruit -> dataFruit.getKey() + DATA_DELIMITER + dataFruit.getValue())
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String getInfoMessage(String expectedMessage, String actualMessage) {
        return "Test failed, expected: " + expectedMessage + " but actual " + actualMessage;
    }
}
