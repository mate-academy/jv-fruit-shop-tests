package core.basesyntax.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileReaderServiceImplTest {
    private static CsvFileReaderServiceImpl csvFileReaderService;

    @BeforeClass
    public static void init() {
        csvFileReaderService = new CsvFileReaderServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_NotValidPath_NotOk() {
        String notValidPath = "src/test/testInput.csv";
        csvFileReaderService.readFromFile(notValidPath);
    }

    @Test
    public void readFromFile_ReadDataCorrespond_Ok() {
        String filePath = "src/test/resources/testInput.csv";
        final List<String[]> actualReadData = csvFileReaderService.readFromFile(filePath);
        String[] columnNames = {"type;fruit;quantity"};
        String[] peachInfo = {"b;peach;10"};
        String[] lemonInfo = {"b;lemon;14"};
        List<String[]> expectedReadData = new ArrayList<>();
        expectedReadData.add(columnNames);
        expectedReadData.add(peachInfo);
        expectedReadData.add(lemonInfo);
        for (int i = 0; i < actualReadData.size(); i++) {
            Assert.assertEquals("Test failed! Actual read data: {" + actualReadData.get(i)[0]
                            + "} don't correspond to expected read data: {"
                            + expectedReadData.get(i)[0] + " }",
                    expectedReadData.get(i)[0], actualReadData.get(i)[0]);
        }
        actualReadData.clear();
        expectedReadData.clear();
    }
}
