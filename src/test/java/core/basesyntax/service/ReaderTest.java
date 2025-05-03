package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderTest {
    private static Reader fileReader;

    @BeforeClass
    public static void before() {
        fileReader = new ReaderImpl();
    }

    @Test (expected = RuntimeException.class)
    public void invalidPath_Not_Ok() {
        String fromFile = "incorrect/path/to/file.csv";
        fileReader.readFromFile(fromFile);
    }

    @Test
    public void correctInputPath_Ok() {
        String fromFile = "src/test/resources/readerTest.csv";
        List<String> expected = List.of("type,name,quantity", "p,apple,20");
        List<String> actual = fileReader.readFromFile(fromFile);
        assertEquals(expected, actual);
    }
}
