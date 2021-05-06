package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.file.FileWriter;
import core.basesyntax.service.file.FileWriterImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    public static final String REPORT_TEST_CSV = "src/test/resources/reportTest.csv";
    private static ReportService reportService;
    private static FileWriter fileWriter;
    private static FruitsDao fruitsDao;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitsDao = new FruitsDaoImpl();
        fileWriter = new FileWriterImpl();
        reportService = new ReportServiceImpl(fileWriter, fruitsDao);
    }

    @Test
    public void createReport_reportFromStorage_Ok() {
        fruitsDao.add(new Fruit("banana"), 20);
        fruitsDao.add(new Fruit("apple"), 50);
        reportService.createReport(REPORT_TEST_CSV);
        List<String> expected = List.of("type,quantity","banana,20","apple,50");
        File file = new File("src/test/resources/reportTest.csv");
        try {
            List<String> actual = Files.readAllLines(file.toPath());
            assertEquals(expected, actual);
            Files.write(file.toPath(),"".getBytes());
        } catch (IOException e) {
            throw new RuntimeException("can't read test file", e);
        }
    }
}
