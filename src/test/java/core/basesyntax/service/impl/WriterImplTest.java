package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.Writer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriterImplTest {
    private StorageDao storageDao = new StorageDaoImpl();
    private ReportGenerator reportGenerator = new ReportGeneratorImpl(storageDao);
    private Writer writer = new WriterImpl(reportGenerator);

    @BeforeAll
    public static void storageSetUp() {
        Storage.fruits.put("banana", 1);
        Storage.fruits.put("orange", 2);
        Storage.fruits.put("pineapple", 3);
    }

    @Test
    void createReport_ok() throws IOException {
        writer.createReport("src/test/resources/OutputFile.csv");
        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new FileReader("src/test/resources/OutputFile.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append(System.lineSeparator());
            }
        }
        String expected = "fruit,quantity"
                + System.lineSeparator() + "banana,1"
                + System.lineSeparator() + "orange,2"
                + System.lineSeparator() + "pineapple,3";
        if (!fileContent.isEmpty()) {
            fileContent.setLength(fileContent.length() - System.lineSeparator().length());
        }
        assertEquals(expected, fileContent.toString(),
                "Method createReport wrong wrote information");
    }

    @Test
    void createReport_nonExistFile_notOk() {
        String nonExistFilePath = "scr/test/resources/nonExistFilePath.csv";
        assertThrows(RuntimeException.class, () -> writer.createReport(nonExistFilePath),
                "Method createReport should throw exception");
    }
}
