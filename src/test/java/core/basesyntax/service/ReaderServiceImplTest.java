package core.basesyntax.service;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static String correctUrl;
    private static String incorrectUrl;
    private static ReaderService readerService;

    @BeforeClass
    public static void beforeClass() {
        readerService = new ReaderServiceImpl();
        incorrectUrl = "src/test/resources/ReaderServiceImpl/nosuchfile.csv";
        correctUrl = "src/test/resources/ReaderServiceImpl/correctinput.csv";
    }

    @Test
    public void read_correctFilePath_ok() {
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
        List<String> actual = readerService.read(Paths.get(correctUrl));
        Assert.assertEquals("Data read error!" + System.lineSeparator()
                + "Expected: " + expected + System.lineSeparator()
                + "Actual:  " + actual, expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_incorrectFilePath_notOk() {
        Path path = Paths.get(incorrectUrl);
        readerService.read(path);
    }
}
