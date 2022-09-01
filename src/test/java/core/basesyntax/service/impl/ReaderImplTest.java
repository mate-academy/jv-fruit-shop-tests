package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.Reader;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderImplTest {
    private static final String PATH = "src/test/resources/fruits.csv";
    private static List<String> expectedResult;
    private static Reader reader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        reader = new ReaderImpl();
        expectedResult = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
    }

    @Test
    public void readFile_Ok() {
        List<String> actual = reader.read(PATH);
        assertEquals(expectedResult, actual);
    }

    @Test(expected = RuntimeException.class)
    public void pathIsNull_NotOk() {
        List<String> actual = reader.read(null);
    }

    @Test(expected = RuntimeException.class)
    public void pathIsNotCorrect_NotOk() {
        List<String> actual = reader.read("path");
    }
}
