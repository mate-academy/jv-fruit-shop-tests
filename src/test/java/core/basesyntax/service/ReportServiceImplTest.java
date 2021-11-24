package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;
    private static String outputTestFilePath =
            "src/test/java/core/basesyntax/test_resources/output_test";

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
        Storage.fruitsCount.put("apple", 10);
    }

    @Test
    public void makeReport_Ok() {
        reportService.makeReport(outputTestFilePath);
        List<String> expected = List.of("fruit,quantity", "apple,10");
        List<String> actual;
        try {
            actual = Files.lines(Path.of(outputTestFilePath))
                          .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from File: " + outputTestFilePath);
        }
        assertEquals(expected, actual);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruitsCount.clear();
    }
}
