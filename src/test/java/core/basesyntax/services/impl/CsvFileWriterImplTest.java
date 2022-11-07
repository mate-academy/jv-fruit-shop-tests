package core.basesyntax.services.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.services.CsvFileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileWriterImplTest {
    private static CsvFileWriter csvFileWriter;

    @BeforeClass
    public static void globalSetUp() {
        csvFileWriter = new CsvFileWriterImpl();
    }

    @Test
    public void createReportFileMethodTest_Ok() throws IOException {
        List<String> expectedList = List.of("fruit,quantity",
                "How are u doing",
                "wass up","100%N....");
        List<String> data = List.of("How are u doing",
                "wass up",
                "100%N....");
        List<String> actualList = Files.readAllLines(Path.of("src/test/testSource/reportTest.csv"));
        csvFileWriter.createReportFile("src/test/testSource/reportTest.csv", data);
        assertEquals(expectedList, actualList);
    }
}
