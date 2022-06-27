
package core.basesyntax.service.impl;

import core.basesyntax.service.Reader;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderImplTest {
    private static final String PATH_TO_INPUT_FILE = "src/main/resources/input.csv";
    private static final String PATH_TO_NOT_EXISTED_FILE = "src/main/resources/inputFile.csv";
    private static Reader reader;

    @BeforeClass
    public static void beforeClass() {
        reader = new ReaderImpl();
    }

    @Test
    public void readInputFile_ok() {
        List<String> expected = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        );
        List<String> actual = reader.getDataFromFile(PATH_TO_INPUT_FILE);
        Assert.assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFileWithInvalidPath_notOk() {
        reader.getDataFromFile(PATH_TO_NOT_EXISTED_FILE);
    }

    @Test(expected = RuntimeException.class)
    public void readFileWithNullPath_notOk() {
        reader.getDataFromFile(null);
    }
}
