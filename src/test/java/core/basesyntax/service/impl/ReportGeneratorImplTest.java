package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static final String SEPARATOR = ",";
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
        storage.addFruit("apple", 5);
        storage.addFruit("banana", 10);
        List<String> formattedList = new ArrayList<>();
        formattedList.add(TITLE);
        storage.getFruits().forEach((fruit, quantity) -> formattedList
                .add(fruit + SEPARATOR + quantity));
        List<String> actual = formattedList;
        List<String> expected = Arrays.asList(
                new String("fruit,quantity"),
                new String("banana,10"),
                new String("apple,5"));
        assertEquals(expected, actual);

    }

    @Test
    void generate_emptyData_isOk() {
        List<String> actual = reportGenerator.generate();
        List<String> expected = Arrays.asList(new String("fruit,quantity"));
        assertEquals(expected, actual);
    }

    @Test
    void generate_oneFruitData_isOk() {
        storage.addFruit("apple", 5);
        List<String> formattedList = new ArrayList<>();
        formattedList.add(TITLE);
        storage.getFruits().forEach((fruit, quantity) -> formattedList
                .add(fruit + SEPARATOR + quantity));
        List<String> actual = formattedList;
        List<String> expected = Arrays.asList(
                new String("fruit,quantity"),
                new String("apple,5"));
        assertEquals(expected, actual);
    }
}
