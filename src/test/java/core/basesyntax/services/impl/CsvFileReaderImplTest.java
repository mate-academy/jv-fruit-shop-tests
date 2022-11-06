package core.basesyntax.services.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.Test;

public class CsvFileReaderImplTest {

    @Test
    public void getAcceptedFileAsList() {
        List<String> expectedList = List.of("How are u doing",
                "wass up", "100%N....");
        List<String> actualList = new CsvFileReaderImpl()
                .getAcceptedFileAsList("src/test/testSource/inputTestFile.csv");
        assertEquals(expectedList, actualList);
    }
}
