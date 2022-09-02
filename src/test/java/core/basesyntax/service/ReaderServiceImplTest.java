package core.basesyntax.service;

import core.basesyntax.service.impl.ReaderServiceImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReaderServiceImplTest {
    private static String correctUrl;
    private static String incorrectUrl;

    @BeforeClass
    public static void beforeClass() throws Exception {
        correctUrl = "src/test/resources/correctinput.csv";
        incorrectUrl = "src/test/resources/nosuchfile.csv";
    }

    @Test
    public void read_correctFilePath_ok() {
        List<String> actual = new ReaderServiceImpl().read(Paths.get(correctUrl));
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
        Assert.assertTrue("Data read error!" + System.lineSeparator()
                + "Expected: " + expected + System.lineSeparator()
                + "Actual:  " + actual, actual.equals(expected));
    }

    @Test(expected = RuntimeException.class)
    public void read_incorrectFilePath_notOk() {
        Path path = Paths.get(incorrectUrl);
        new ReaderServiceImpl().read(path);
    }
}
