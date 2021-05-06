package core.basesyntax;

import core.basesyntax.database.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportCreator;
import core.basesyntax.service.implementions.ReportCreatorImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorImplTest {
    private static final String FILE_REPORT = "src/test/java/resources/reportPerfect.csv";
    private static ReportCreator reportCreator;
    private static Fruit apple;
    private static Fruit banana;

    @BeforeClass
    public static void beforeClass() {
        reportCreator = new ReportCreatorImpl();
        apple = new Fruit("apple");
        banana = new Fruit("banana");
    }

    @Test
    public void createReport_isOk() {
        Storage.fruitStorage.put(apple, 90);
        Storage.fruitStorage.put(banana, 152);

        String expectedReport = reportCreator.createReport(Storage.fruitStorage);
        String actualReport = readFromFile(FILE_REPORT);
        Assert.assertEquals(expectedReport, actualReport);
    }

    @Test (expected = RuntimeException.class)
    public void createReport_NotOk() {
        Storage.fruitStorage.put(apple, 90);
        Storage.fruitStorage.put(banana, 152);
        Storage.fruitStorage.put(null, null);

        String expectedReport = reportCreator.createReport(Storage.fruitStorage);
        String actualReport = readFromFile(FILE_REPORT);
        Assert.assertEquals(expectedReport, actualReport);
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }

    private String readFromFile(String fileName) {
        try {
            return Files.readString(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file " + fileName, e);
        }
    }
}
