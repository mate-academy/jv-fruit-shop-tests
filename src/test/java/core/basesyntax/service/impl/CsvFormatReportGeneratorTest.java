package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportGenerator;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvFormatReportGeneratorTest {
    private static Storage storage;
    private static ReportGenerator csvFormatReportGenerator;

    @BeforeAll
    static void beforeAll() {
        storage = new Storage(new HashMap<>());
        FruitStorageDaoImpl fruitStorageDao = new FruitStorageDaoImpl(storage);
        csvFormatReportGenerator = new CsvFormatReportGenerator(fruitStorageDao);
    }

    @BeforeEach
    void setUp() {
        storage.getFruits().clear();
    }

    @Test
    void getReport_validData_ok() {
        storage.getFruits().put(new Fruit("banana"), 152);
        storage.getFruits().put(new Fruit("apple"), 90);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator() + "apple,90";
        String actual = csvFormatReportGenerator.getReport();
        assertEquals(expected, actual);
    }
}
