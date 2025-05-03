package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.Reader;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String INPUT_FILE_PATH = "src/test/java/resources/file";
    private static Reader reader;
    private List<String> string = List.of("type,fruit,quantity",
            "b,banana,20",
            "b,apple,100",
            "s,banana,100",
            "p,banana,13",
            "r,apple,10",
            "p,apple,20",
            "p,banana,5",
            "s,banana,50");

    @BeforeClass
    public static void beforeClass() throws Exception {
        reader = new ReaderServiceImpl();
    }

    @Test
    public void readerServiceIsValid_Ok() {
        List<String> actual = reader.read(INPUT_FILE_PATH);
        List<String> expected = string;
        assertEquals(expected,actual);
    }

    @Test (expected = RuntimeException.class)
    public void readerIsValid_NotOk() {
        reader.read("can't read file");
    }

    @Test (expected = RuntimeException.class)
    public void readerIsNotValidNull() {
        reader.read(null);
    }
}
