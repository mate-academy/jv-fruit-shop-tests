package core.basesyntax.services.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.Test;

public class CsvFileWriterImplTest {

    @Test
    public void createReportFileMethodTest_Ok() {
        List<String> expectedList = List.of("fruit,quantity",
                "How are u doing",
                "wass up","100%N....");
        List<String> data = List.of("How are u doing","wass up","100%N....");
        new CsvFileWriterImpl().createReportFile("src/test/testSource/reportTest.csv", data);
        List<String> actualList = new CsvFileReaderImpl()
                .getAcceptedFileAsList("src/test/testSource/reportTest.csv");
        assertEquals(expectedList, actualList);
    }
}
