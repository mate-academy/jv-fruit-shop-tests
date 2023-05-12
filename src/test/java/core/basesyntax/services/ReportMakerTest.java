package core.basesyntax.services;

import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.ReportMakerService;
import core.basesyntax.service.impl.FileWriterServiceImpl;
import core.basesyntax.service.impl.ReportMakerServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportMakerTest {
    private static final String PATH = "src/test/resources";
    private static Map<String, Integer> storage;
    private static FileWriterService fileWriterService;
    private static ReportMakerService reportMakerService;

    @BeforeClass
    public static void beforeClass() {
        fileWriterService = new FileWriterServiceImpl();
        reportMakerService = new ReportMakerServiceImpl(fileWriterService);
        storage = new HashMap<>();
    }

    @After
    public void tearDown() {
        storage.clear();
    }

    @Test
    public void createReport_validData_Ok() {
        storage.put("banana", 400);
        storage.put("apple", 600);
        storage.put("mango", 200);
        Path report = reportMakerService.createReport(storage, PATH);
        List<String> actual;
        try {
            actual = Files.readAllLines(report);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<String> expected = List.of(
                "fruit,quantity",
                "banana,400",
                "apple,600",
                "mango,200"
        );
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void createReport_invalidData_notOk() {
        reportMakerService.createReport(null, null);
    }
}
