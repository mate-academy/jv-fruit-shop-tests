package service;

import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ReaderImplTest {
    private static final String FILE_SEPARATOR = FileSystems.getDefault().getSeparator();
    private static final String INPUT_FILE = "src" + FILE_SEPARATOR
            + "test" + FILE_SEPARATOR + "resources" + FILE_SEPARATOR + "testInput.csv";
    private static final String INVALID_PATH = "";
    private static Reader reader;

    @Test
    public void readFromFile_Ok() {
        reader = new ReaderImpl();
        List<String> expected = new ArrayList<>();
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
        List<String> actual = reader.read(INPUT_FILE);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void invalidFilePath_NotOk() {
        reader.read(INVALID_PATH);
    }
}
