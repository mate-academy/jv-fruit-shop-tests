package core.basesyntax.service.cvs;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String FILE_IN = "src/test/resources/fruit-shop-test.csv";
    private static final String FILE_PATH_INVALID = "src/test/resources/shop/fruit-shop-test.csv";
    private static final String INVALID_FILE = "src/test/resources/fruit-shop-test";
    private FileReader fileReader;

    @Before
    public void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readInvalidFilePath_NotOk() {
        fileReader.read(FILE_PATH_INVALID);
    }

    @Test(expected = RuntimeException.class)
    public void readInvalidFile_NotOk() {
        fileReader.read(INVALID_FILE);
    }

    @Test
    public void readFile_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,orange,30");
        expected.add("r,apple,25");
        expected.add("s,banana,25");
        expected.add("p,orange,5");
        List<String> actual = fileReader.read(FILE_IN);
        assertEquals("Test failed! Size of list should be " + expected.size() + " but it is "
                        + actual.size(), expected.size(), actual.size());
        assertEquals(expected, actual);
    }
}
