package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static final String COMA_SEPARATOR = ",";
    private static final String TITLE = "fruit,quantity";
    private Storage storage;
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        reportGenerator = new ReportGeneratorImpl(storage);
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }

    @Test
    void generate_dataIsValid_isOk() {
        storage.addFruit("banana", 10);
        storage.addFruit("apple", 5);
        List<String> actual = reportGenerator.generate();
        List<String> expected = List.of(
                "fruit,quantity",
                "banana,10",
                "apple,5");
        assertEquals(expected, actual);

    }

    @Test
    void generate_emptyData_isOk() {
        List<String> actual = reportGenerator.generate();
        List<String> expected = List.of(
                "fruit,quantity");
        assertEquals(expected, actual);
    }

    @Test
    void generate_singleFruitInData_isOk() {
        storage.addFruit("banana", 10);
        List<String> actual = reportGenerator.generate();
        List<String> expected = List.of(
                "fruit,quantity",
                "banana,10");
        assertEquals(expected, actual);
    }

    @Test
    void generate_fruitWithZeroQuantity_isOk() {
        storage.addFruit("banana", 0);
        storage.addFruit("apple", 0);
        List<String> actual = reportGenerator.generate();
        List<String> expected = List.of(
                "fruit,quantity",
                "banana,0",
                "apple,0");
        assertEquals(expected, actual);
    }
}
