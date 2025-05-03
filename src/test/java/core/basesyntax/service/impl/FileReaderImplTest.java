package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReader;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FileReaderImplTest {
    public static final String READABLE_FILEPATH = "src/test/resources/input.csv";
    public static final String EMPTY_FILEPATH = "";
    private FileReader reader;

    @Before
    public void setUp() {
        reader = new FileReaderImpl();
    }

    @Test
    public void readFromFile_ok() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20", "b,apple,100",
                "s,banana,100", "p,banana,13", "r,apple,10", "p,apple,20", "p,banana,5",
                "s,banana,50");
        List<String> actual = reader.readData(READABLE_FILEPATH);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromIncorrectFile_notOk() {
        reader.readData(EMPTY_FILEPATH);
    }
}
