package core.basesyntax;

import core.basesyntax.service.ReaderFromFile;
import core.basesyntax.service.impl.ReaderFromFileImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderFromFileTest {
    private static String pathToFile;
    private static String line1;
    private static String line2;
    private static ReaderFromFile reader;

    @BeforeClass
    public static void beforeClass() {
        pathToFile = "test.csv";
        reader = new ReaderFromFileImpl();
        line1 = "Correct";
        line2 = "Data";
    }

    @Test(expected = RuntimeException.class)
    public void incorrectPathToFile_notOk() {
        reader.getData(pathToFile);
    }

    @Test
    public void correctPathToFile_ok() {
        pathToFile = "src/main/resources/inputData.csv";
        reader.getData(pathToFile);
    }

    @Test
    public void correctAddingDataToList_oK() {
        pathToFile = "src/test/files/correctAddingDataToList";
        Assert.assertEquals(reader.getData(pathToFile).get(0), line1);
        Assert.assertEquals(reader.getData(pathToFile).get(1), line2);
    }
}
