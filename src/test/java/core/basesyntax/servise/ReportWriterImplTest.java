package core.basesyntax.servise;

import core.basesyntax.model.Fruit;
import core.basesyntax.servise.inrterfase.ReportWriter;
import core.basesyntax.storage.Storage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportWriterImplTest {
    private static final Fruit banana = new Fruit("banana");
    private static final Fruit apple = new Fruit("apple");
    private static final Fruit orange = new Fruit("orange");
    private static final String DIRECT_TO_OUTPUT_FILE
            = "src/test/resources/outputReportTest.csv";
    private static final String DIRECT_TO_OUTPUT_EMPTY_FILE
            = "src/test/resources/outputEmptyReportTest.csv";
    private static final String INVALID_PATH
            = "srv/test/java/core/basesyntax/resources/newFile.csv";
    private ReportWriter writer;

    @Before
    public void setUp() throws Exception {
        writer = new ReportWriterImpl();
        Storage.storageOfFruits.clear();
    }

    @Test
    public void writeReport_NormalWork_Ok() {
        Storage.storageOfFruits.put(banana, 100);
        Storage.storageOfFruits.put(apple, 50);
        Storage.storageOfFruits.put(orange, 0);
        writer.writeReport(DIRECT_TO_OUTPUT_FILE);
        String actualResult = readFromFile(DIRECT_TO_OUTPUT_FILE).trim();
        String expectedResult = "fruit,quantity" + System.lineSeparator()
                + "banana,100" + System.lineSeparator()
                + "apple,50" + System.lineSeparator()
                + "orange,0";
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void writeReport_WriteEmptyFile_Ok() {
        writer.writeReport(DIRECT_TO_OUTPUT_EMPTY_FILE);
        String actualResult = readFromFile(DIRECT_TO_OUTPUT_EMPTY_FILE).trim();
        String expectedResult = "fruit,quantity";
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test(expected = RuntimeException.class)
    public void testWriteToFileWithFalsePath_assertException() {
        writer.writeReport(INVALID_PATH);
    }

    private String readFromFile(String fileName) {
        try {
            return Files.readString(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly read data from file " + fileName, e);
        }
    }
}
