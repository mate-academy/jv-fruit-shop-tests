package core.basesyntax.service.read;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class FileReaderImplTest {
    private FileReader fileReader = new FileReaderImpl();

    @Test
    public void read_readFile_Ok() {
        List<String> actual = fileReader.read("src/test/java/resources/test_correct.csv");
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13");
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void read_readEmptyFile_Ok() {
        List<String> actual = fileReader.read("src/test/java/resources/empty.csv");
        List<String> expected = new ArrayList<>();
        Assert.assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void read_readInvalidFilePath_ExceptionOk() {
        List<String> actual = fileReader.read("src/test/java/resources/invalidFilePath.csv");
    }





}
