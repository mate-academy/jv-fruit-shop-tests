package core.basesyntax;

import core.basesyntax.service.ReaderFromFile;
import core.basesyntax.service.impl.ReaderFromFileImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderFromFileTest {
    private static String pathToFile;
    private static ReaderFromFile reader;
    private static List<String> expected;

    @BeforeClass
    public static void beforeClass() {
        reader = new ReaderFromFileImpl();
    }

    @Test(expected = RuntimeException.class)
    public void incorrectPathToFile_notOk() {
        pathToFile = "test.csv";
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
        expected = new ArrayList<>();
        expected.add("Correct");
        expected.add("Data");
        List<String> actual = reader.getData(pathToFile);
        Assert.assertEquals(expected, actual);
    }
}
