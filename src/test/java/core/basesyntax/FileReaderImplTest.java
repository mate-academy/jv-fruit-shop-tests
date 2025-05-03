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
    private static final String INPUT_FILE_PATH = "src/test/resources/input.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/empty.csv";
    private static final String FAKE_FILE_PATH = "src/test/***/resources/input.csv";

    @BeforeClass
    public static void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void read_fromFile_ok() {
        List<String> expected = List.of("type,fruit,quantity","b,banana,20",
                "b,apple,100", "s,banana,100", "p,banana,13",
                "r,apple,10", "p,apple,20",
                "p,banana,5", "s,banana,50");
        List<String> actual = fileReader.read(INPUT_FILE_PATH);
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void read_fromEmptyFile_Ok() {
        List<String> expected = new ArrayList<>();
        List<String> actual = fileReader.read(EMPTY_FILE_PATH);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_wrongFilePath_notOk() {
        fileReader.read(FAKE_FILE_PATH);
    }
}
