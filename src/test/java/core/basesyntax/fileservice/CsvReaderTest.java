package core.basesyntax.fileservice;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvReaderTest {
    private static final CsvReader reader = new CsvReader();
    private static final String FILE_NAME = "shop_operations_test.csv";
    private static List<String> testList = new ArrayList<>();

    @BeforeClass
    public static void beforeClass() throws Exception {
        testList.add("b,banana,20");
        testList.add("b,apple,100");
        testList.add("s,banana,100");
    }

    @Test
    public void readFromFile_OK() {
        List<String> actual = reader.readFromFile(FILE_NAME);
        assertEquals(testList, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFileNotExistPath_NotOk() {
        reader.readFromFile("");
    }
}
