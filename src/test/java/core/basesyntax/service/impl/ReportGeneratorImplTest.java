package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
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
        Map<String, Integer> testDataFruits = Map.of(
                "apple", 5,
                "banana", 10
        );
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

    @AfterEach
    void tearDown() {
        Storage.getFruits().forEach(Storage::removeFruit);
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
}
