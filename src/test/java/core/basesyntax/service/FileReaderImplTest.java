package core.basesyntax.service;

import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static FileReader reader;

    @BeforeClass
    public static void beforeClass() {
        reader = new FileReaderImpl();
    }

    @Test
    public void reader_correctData_OK() {
        List<String> expected = List.of("Correct data");
        List<String> actual = reader.readFromFile("src/test/resources/readerTest.csv");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void reader_notCorrectData_not_ok() {
        boolean actual = reader.readFromFile("src/test/resources/readerTest.csv")
                .equals(List.of("someData"));
        Assert.assertFalse(actual);
    }

    @Test(expected = RuntimeException.class)
    public void reader_incorrectFilePath_not_ok() {
        reader.readFromFile("");
    }
}
