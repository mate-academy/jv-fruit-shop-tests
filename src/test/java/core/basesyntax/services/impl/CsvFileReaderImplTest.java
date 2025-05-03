package core.basesyntax.services.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.services.CsvFileReader;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileReaderImplTest {
    private static CsvFileReader csvFileReader;

    @BeforeClass
    public static void globalSetUp() {
        csvFileReader = new CsvFileReaderImpl();
    }

    @Test
    public void getAcceptedFileAsListMethodTest_ok() {
        List<String> expectedList = List.of("How are u doing",
                "wass up", "100%N....");
        List<String> actualList = csvFileReader
                .getAcceptedFileAsList("src/test/testSource/inputTestFile.csv");
        assertEquals(expectedList, actualList);
    }
}
