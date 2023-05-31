package core.basesyntax.service;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceTest {
    private static ReaderService reader;
    private static final String PATH_OF_TEST_FILE = "src/main/resources/testReader.csv";

    @BeforeClass
    public static void beforeClass() {
        reader = new ReaderServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_NullFile_NotOk() {
        reader.readFromFile(null);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_NotExistedFile_NotOk() {
        reader.readFromFile("src/main/resources/notExistedFile.csv");
    }

    @Test
    public void readFromFile_File_Ok() {
        List<String> expected = List.of("apple", "peach");
        List<String> actual = reader.readFromFile(PATH_OF_TEST_FILE);
        Assert.assertEquals(expected, actual);
    }
}
