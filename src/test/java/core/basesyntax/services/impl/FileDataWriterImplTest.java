package core.basesyntax.services.impl;

import core.basesyntax.services.ReportGenerator;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

class FileDataWriterImplTest {
    private static final String expectedString = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "apple,90" + System.lineSeparator();

    private ReportGenerator reportGenerator;
    private Storage storage;
    private Map<String, Integer> storageNew;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        storageNew = storage.getStorage();
        reportGenerator = new ReportGeneratorImpl(storage);
    }

    @AfterEach
    void tearDown() {
        storage.clearAll();
    }

    @Test
    void writeToFile_validFile_ok() {
        storageNew.put("banana", 152);
        storageNew.put("apple", 90);
        String actualString = reportGenerator.getReport();
        Assertions.assertEquals(expectedString, actualString);
    }
}
