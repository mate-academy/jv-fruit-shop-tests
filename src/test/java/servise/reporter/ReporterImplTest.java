package servise.reporter;

import db.Storage;
import model.Fruit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReporterImplTest {
    private final Reporter reporter;

    private ReporterImplTest() {
        this.reporter = new ReporterImpl();
    }

    @Test
    void createReporter_ok() {
        Storage.data.put(new Fruit("apple"), 20);
        Storage.data.put(new Fruit("banana"), 10);
        Storage.data.put(new Fruit("orange"), 7);
        String expected = createReport();
        String actual = reporter.createReport();
        Assertions.assertEquals(expected, actual);
    }

    private String createReport() {
        StringBuilder report = new StringBuilder("fruit,quantity" + System.lineSeparator());
        for (Fruit fruitName : Storage.data.keySet()) {
            report.append(fruitName.getName())
                    .append(",")
                    .append(Storage.data.get(fruitName))
                    .append(System.lineSeparator());
        }
        return report.toString();
    }
}
