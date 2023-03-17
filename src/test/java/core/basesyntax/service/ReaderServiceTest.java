package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.ReaderServiceImpl;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ReaderServiceTest {
    private static final String PATH = pathFix("src/test/resources/fruits.csv");
    private static final String EMPTY_PATH = "";
    private static ReaderService readerService;

    @Before
    public void setUp() throws Exception {
        readerService = new ReaderServiceImpl();
    }

    @Test
    public void readFromFile_Ok() {
        File file = new File(PATH);
        readerService = new ReaderServiceImpl();
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
        List<String> actual = readerService.readFromFile(PATH);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readWithEmptyPath_NotOk() {
        readerService.readFromFile(EMPTY_PATH);
    }

    private static String pathFix(String path) {
        path = path.replace("\\", File.separator);
        path = path.replace("/", File.separator);
        return path;
    }
}
