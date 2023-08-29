package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.ReportServiceImpl;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReportServiceTest {
    private static ReportService reportService;
    private static final String HEAD = "fruit,quantity";
    private static final String SEPARATOR = ",";

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImpl(new FruitDaoImpl());
    }

    @Test
    void report_ok() {
        Fruit apple = new Fruit("apple", 20);
        Fruit banana = new Fruit("banana", 142);
        Storage.getFruits().add(apple);
        Storage.getFruits().add(banana);
        String expected = HEAD + System.lineSeparator()
                + Storage.getFruits()
                .stream()
                .map(fruit -> fruit.getName() + SEPARATOR + fruit.getQuantity())
                .collect(Collectors.joining(System.lineSeparator()));;
        String actual = reportService.createReport();
        assertEquals(expected, actual);
    }
}
