package core.basesyntax;

import core.basesyntax.service.impl.ReaderImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderImplTest {
    private static final String VALID_PATH =
            "src/test/java/core/basesyntax/resources/valid.csv";
    private static final String INVALID =
            "src/test/java/core/basesyntax/resources/notValid.csv";
    private static final String EMPTY =
            "src/test/java/core/basesyntax/resources/empty.csv";
    private static final String NOT =
            "src/test/java/core/basesyntax/resources/no_such_file.csv";
    private static ReaderImpl reader;

    @BeforeClass
    public static void initialization() {
        reader = new ReaderImpl();
    }

    @Test
    public void reader_ValidPath_OK() {
        List<String> expected = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        List<String> actual = reader.readFromFile(VALID_PATH);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void reader_emptyFile_OK() {
        List<String> expected = new ArrayList<>();
        List<String> actual = reader.readFromFile(EMPTY);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void reader_invalidFile_OK() {
        List<String> expected = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,**");
        List<String> actual = reader.readFromFile(INVALID);
        Assert.assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void reader_notExistingFile_notOK() {
        reader.readFromFile(NOT);
    }
}
