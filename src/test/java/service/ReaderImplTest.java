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

    @Test
    public void readFromFile_Ok() {
        List<String> result;
        Reader reader;
        reader = new ReaderImpl();
        result = new ArrayList<>();
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
