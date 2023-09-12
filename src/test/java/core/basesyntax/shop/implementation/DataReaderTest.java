package core.basesyntax.shop.implementation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.shop.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataReaderTest {
    private static final String TEST_FILE_DATA = "src/test/java/core/"
            + "basesyntax/resources/fileWithInputData.csv";
    private static final String TEST_FILE_INCORRECT = "src/test/java/core/"
            + "basesyntax/resources/testFiles.txt";
    private static FileReader dataReader;

    @BeforeClass
    public static void beforeClass() {
        dataReader = new DataReader();
    }

    @Test
    public void readFromInputData_Ok() {
        List<String> expected;
        try {
            expected = Files.readAllLines(Path.of(TEST_FILE_DATA));
        } catch (IOException e) {
            throw new RuntimeException("Can't find test file: " + TEST_FILE_DATA,e);
        }
        List<String> actual = dataReader.readFromInputData(TEST_FILE_DATA);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromInputData_NotOk() {
        dataReader.readFromInputData(TEST_FILE_INCORRECT);
    }
}
