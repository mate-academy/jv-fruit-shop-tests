package core.basesyntax.servise.reader;

import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReaderImplTest {
    private static final String PATH_INPUT_FILE = "src/test/resources/inputData.csv";
    private Reader reader;

    @Before
    public void setUp() {
        reader = new ReaderImpl();
    }

    @Test
    public void readFromFile_ok() {
        List<String> expected = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        List<String> actual = reader.readFromFile(PATH_INPUT_FILE);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_NotValidPath() {
        reader.readFromFile("invalid/directory/file.csv");
    }
}
