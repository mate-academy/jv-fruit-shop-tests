package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.Reader;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderImplTest {
    private static final String VALID_PATH = "src/test/resources/inputDataTest.csv";
    private static final String INVALID_PATH = "src//test//resources//inputData.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/emptyDataTest.csv";
    private static Reader reader;

    @BeforeClass
    public static void setUp() {
        reader = new ReaderImpl();
    }

    @Test
    public void read_correctFile_ok() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,30",
                "b,apple,50", "s,banana,10", "p,apple,4");
        List<String> actual = reader.readFile(VALID_PATH);
        assertEquals(actual, expected);
    }

    @Test (expected = RuntimeException.class)
    public void read_notCorrectPath_notOk() {
        reader.readFile(INVALID_PATH);
    }

    @Test
    public void read_emptyDataFile_ok() {
        List<String> expected = List.of();
        List<String> actual = reader.readFile(EMPTY_FILE_PATH);
        assertEquals(expected, actual);
    }
}
