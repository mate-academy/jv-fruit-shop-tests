package core.basesyntax;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportCreator;
import core.basesyntax.service.ReportCreatorImpl;
import core.basesyntax.service.ServiceWriter;
import core.basesyntax.service.ServiceWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileWriterImplTest {
    private static final String EXPECTED_REPORT = "src/test/resources/expected_file.csv";
    private static final String PATH_TO_WRITE = "src/test/resources/file_to_write.csv";
    private static final Map<Fruit, Integer> fruits = new HashMap<>();
    private static final ServiceWriter fileWriter = new ServiceWriterImpl();
    private static final ReportCreator reportCreator = new ReportCreatorImpl();

    @BeforeClass
    public static void setUp() {
        fruits.put(new Fruit("banana"), 731);
        fruits.put(new Fruit("apple"), 157);
    }

    @Test
    public void fileWriterCheck_Ok() {
        fileWriter.writeData(PATH_TO_WRITE, reportCreator.getFruitsReport(fileWriter, fruits));
        List<String> actualData;
        List<String> expectedData;
        try {
            actualData = Files.readAllLines(Path.of(PATH_TO_WRITE));
            expectedData = Files.readAllLines(Path.of(EXPECTED_REPORT));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from files" + PATH_TO_WRITE, e);
        }
        Assert.assertEquals(actualData.size(), expectedData.size());
        Assert.assertEquals(expectedData, actualData);
    }
}
