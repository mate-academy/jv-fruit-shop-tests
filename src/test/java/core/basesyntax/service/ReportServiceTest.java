package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import core.basesyntax.dto.Fruit;
import core.basesyntax.dto.Operation;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Test;

public class ReportServiceTest {
    private static final ReportService reportBuilder = new ReportService();

    @Test
    public void buildReport_Ok() {
        StorageService storage = new StorageService();
        Fruit apple = new Fruit("apple");
        Fruit banana = new Fruit("banana");
        storage.create(apple, new Operation(Operation.OperationType.BALANCE, apple, 20));
        storage.create(banana, new Operation(Operation.OperationType.BALANCE, banana, 35));

        String expected = "banana,35" + System.lineSeparator() + "apple,20"
                + System.lineSeparator();
        String actual = reportBuilder.buildReport(storage);
        assertEquals(expected, actual);
    }

    @Test
    public void writeData_Ok() throws IOException {
        StorageService storage = new StorageService();
        Fruit apple = new Fruit("apple");
        Fruit banana = new Fruit("banana");
        storage.create(apple, new Operation(Operation.OperationType.BALANCE, apple, 20));
        storage.create(banana, new Operation(Operation.OperationType.BALANCE, banana, 35));

        String report = reportBuilder.buildReport(storage);
        reportBuilder.writeData("src/test/test_write_data.csv", report);
        try {
            List<String> result = Files.readAllLines(Path.of("src/test/test_write_data.csv"));
            assertNotNull(result);
        } catch (IOException e) {
            throw new IOException("File doesn't exists " + e);
        }
    }
}
