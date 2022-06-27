package basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import basesyntax.service.Reader;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderImplTest {
    private static final String DATA_DURING_DAY =
            "src/test/java/resources/DataDuringDay.csv";
    private static final String EMPTY_FILE =
            "src/test/java/resources/EmptyFile.csv";
    private static final String INVALID_PATH_FILE =
            "src/test/java/resources/InvalidPath.csv";
    private static Reader reader;

    @BeforeClass
    public static void beforeClass() {
        reader = new ReaderImpl();
    }

    @Test
    public void fileReader_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
        List<String> actual = reader.fileReader(DATA_DURING_DAY);
        assertEquals(expected, actual);
    }

    @Test
    public void fileReader_emptyFile_Ok() {
        List<String> expected = new ArrayList<>();
        List<String> actual = reader.fileReader(EMPTY_FILE);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void fileReader_invalidPathFile_notOk() {
        reader.fileReader(INVALID_PATH_FILE);
    }
}
