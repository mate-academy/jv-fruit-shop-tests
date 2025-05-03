package core.basesyntax.service.impl;

import core.basesyntax.service.Reader;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderImplTest {
    private static Reader reader;

    @BeforeClass
    public static void beforeClass() {
        reader = new ReaderImpl();
    }

    @Test
    public void readFromFile_correctFile_ok() {
        List<String> actual = reader.readFromFile("src/test/resources/readerTest1.csv");
        List<String> expected = List.of("test", "b,banana,1");
        Assert.assertEquals(actual, expected);
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_emptyFilePath_notOk() {
        reader.readFromFile("");
    }

    @Test (expected = RuntimeException.class)
    public void readFromFile_nullFilePath_notOk() {
        reader.readFromFile(null);
    }
}
