package service;

import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderImplTest {
    private static final String FILE_SEPARATOR = FileSystems.getDefault().getSeparator();
    private static final String INPUT_FILE = "src" + FILE_SEPARATOR
            + "test" + FILE_SEPARATOR + "resources" + FILE_SEPARATOR + "testInput.csv";
    private static List<String> result;
    private static Reader reader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        reader = new ReaderImpl();
        result = new ArrayList<>();
    }

    @Test
    public void readFromFile_OK() {
        result.add("b,banana,20");
        result.add("b,apple,100");
        result.add("s,banana,100");
        result.add("p,banana,13");
        result.add("r,apple,10");
        result.add("p,apple,20");
        result.add("p,banana,5");
        result.add("s,banana,50");
        List<String> actual = reader.read(INPUT_FILE);
        Assert.assertEquals(result, actual);
    }
}
