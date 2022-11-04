package core.basesyntax;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.impl.FileReaderImpl;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static FileReader fileReader;
    private static final String INPUT_FILE_PATH = "src/main/resources/input.csv";
    private static final String FAKE_FILE_PATH = "src/main/***/resources/input.csv";

    @BeforeClass
    public static void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void read_FromFile_Ok() {
        List<String> expected = List.of("b,banana,20", "b,apple,100", "s,banana,100",
                 "p,banana,13", "r,apple,10", "p,apple,20", "p,banana,5", "s,banana,50");
        List<String> actual = fileReader.read(INPUT_FILE_PATH);
        actual.remove(0);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeWrongFile_notOk() {
        fileReader.read(FAKE_FILE_PATH);
    }
}
