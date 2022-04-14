package core.basesyntax.service.impl;

import core.basesyntax.service.MyFileReader;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class MyFileReaderImplTest {
    private static MyFileReader reader;

    @BeforeClass
    public static void beforeClass() {
        reader = new MyFileReaderImpl();
    }

    @Test
    public void reader_correctData_ok() {
        List<String> expected = List.of("10","20","30");
        List<String> actual = reader.readFromFile("src/test/resourses/textReaderText.csv");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void reader_notCorrectData_notOk() {
        boolean actual = reader.readFromFile("src/test/resourses/textReaderText.csv")
                .equals(List.of("a"));
        Assert.assertFalse(actual);
    }

    @Test (expected = RuntimeException.class)
    public void reader_notFile_notOk() {
        reader.readFromFile("");
    }
}
