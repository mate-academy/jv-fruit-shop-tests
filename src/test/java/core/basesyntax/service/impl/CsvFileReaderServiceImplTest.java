package core.basesyntax.service.impl;

import core.basesyntax.service.CsvFileReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileReaderServiceImplTest {
    private static CsvFileReaderService csvFileReaderService;
    private static final String NOT_VALID_PATH = "src/test/testInput.csv";
    private static final String TEST_FILEPATH = "src/test/resources/testInput.csv";

    @BeforeClass
    public static void init() {
        csvFileReaderService = new CsvFileReaderServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_NotValidPath_NotOk() {
        csvFileReaderService.readFromFile(NOT_VALID_PATH);
    }

    @Test
    public void readFromFile_ReadDataCorrespond_Ok() {
        List<String[]> expectedReadData = new ArrayList<>();
        expectedReadData.add(new String[] {"type;fruit;quantity"});
        expectedReadData.add(new String[] {"b;peach;10"});
        expectedReadData.add(new String[] {"b;lemon;14"});
        List<String[]> actualReadData = csvFileReaderService.readFromFile(TEST_FILEPATH);
        for (int i = 0; i < actualReadData.size(); i++) {
            Assert.assertEquals("Test failed! Actual read data: {" + actualReadData.get(i)[0]
                            + "} don't correspond to expected read data: {"
                            + expectedReadData.get(i)[0] + " }",
                    expectedReadData.get(i)[0], actualReadData.get(i)[0]);
        }
    }
}
