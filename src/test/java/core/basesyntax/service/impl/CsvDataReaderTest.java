package core.basesyntax.service.impl;

import core.basesyntax.service.ReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvDataReaderTest {

    private static ReaderService readerService;

    @BeforeAll
    public static void setUp() {
        readerService = new CsvDataReader();
    }

    @Test
    public void readFromFile_invalidPath_notOk() {
        String invalidPath = "src/tes/resources/inputTest.csv";
        Assert.assertThrows(RuntimeException.class, () -> readerService.readFromFile(invalidPath));
    }

    @Test
    public void readFromFile_validPath_Ok() {
        String validPath = "src/test/resources/inputTest.csv";
        List<String[]> expected = new ArrayList<>();
        expected.add(new String[]{"type", "fruit", "quantity"});
        expected.add(new String[]{"b", "banana", "20"});
        expected.add(new String[]{"b", "apple", "100"});
        expected.add(new String[]{"s", "banana", "100"});
        expected.add(new String[]{"p", "banana", "13"});
        List<String[]> actual = readerService.readFromFile(validPath);
        for (int i = 0; i < expected.size(); i++) {
            String[] expectedArray = expected.get(i);
            String[] actualArray = actual.get(i);
            for (int j = 0; j < expectedArray.length; j++) {
                Assert.assertEquals(expectedArray[j], actualArray[j]);
            }
        }
    }
}
