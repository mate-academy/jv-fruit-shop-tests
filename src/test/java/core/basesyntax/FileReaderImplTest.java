package core.basesyntax;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.impl.FileReaderImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static FileReader fileReader;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void reader_readFile_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
        List<String> actual =
                fileReader.readFromFile("src/test/resources/shop_operations_test.csv");
        Assert.assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void reader_readEmptyFile_Ok() {
        List<String> expected = new ArrayList<>();
        List<String> actual =
                fileReader.readFromFile("src/test/resources/shop_operations_empty_test.csv");
        Assert.assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test(expected = RuntimeException.class)
    public void reader_readFile_notOk() {
        fileReader.readFromFile("incorrect file name");
    }
}
