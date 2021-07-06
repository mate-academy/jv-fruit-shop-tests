package core.basesyntax.service;

import static org.junit.Assert.assertTrue;

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
    public void readFromFile_IncorrectPath_Not_Ok() {
        String fromFile = "incorrect/path/to/file.csv";
        fileReader.readFromFile(fromFile);
    }

    @Test
    public void readFromFile_CorrectInputPath_Ok() {
        String fromFile = "src/test/resources/readerTest.csv";
        List<String> expected = List.of("type,name,quantity", "p,apple,20");
        List<String> actual = fileReader.readFromFile(fromFile);
        assertTrue(actual.size() == expected.size()
                && actual.containsAll(expected));
    }
}
