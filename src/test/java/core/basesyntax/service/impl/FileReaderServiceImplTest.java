package core.basesyntax.service.impl;

import core.basesyntax.service.FileReaderService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static FileReaderService fileReader;
    private static final String WAREHOUSE_PATH = "src/test/resources/warehouse.csv";
    private static final String WRONG_WAREHOUSE_PATH = "src/test/resources/warehouse-not-exist.csv";
    private static final String EMPTY_WAREHOUSE_PATH = "src/test/resources/empty-warehouse.csv";

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderServiceImpl();
    }

    @Test
    public void readFile_Ok() {
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
        List<String> actual = fileReader.read(WAREHOUSE_PATH);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void readEmptyFile_Ok() {
        List<String> actual = fileReader.read(EMPTY_WAREHOUSE_PATH);
        List<String> expected = new ArrayList<>();
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFileInvalidPath_notOk() {
        fileReader.read(WRONG_WAREHOUSE_PATH);
    }
}
