package core.basesyntax.service.read;

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
}
