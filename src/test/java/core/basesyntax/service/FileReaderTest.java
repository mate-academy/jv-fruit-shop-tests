package core.basesyntax.service;

import core.basesyntax.service.impl.FileReaderImpl;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderTest {
    private static FileReader fileReader;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void read_inputCorrectFile_Ok() {
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20", "b,apple,100");
        List<String> actual = fileReader.readFromFile("src/test/java/resources/input_file.csv");
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_nonExistentPath_NotOk() {
        fileReader.readFromFile("src/test/java/resources/non-existent.csv");
    }
}
