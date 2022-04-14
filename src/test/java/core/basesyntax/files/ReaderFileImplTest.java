package core.basesyntax.files;

import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderFileImplTest {
    private static final String SOURCE_FILE_GOOD = "src/test/resources/test_reader_file_impl.csv";
    private static final String SOURCE_FILE_BAD = "src/test/resources/NOT_EXIST.csv";
    private static Reader readerFileExist;
    private static Reader readerFileDoNotExist;

    @BeforeClass
    public static void beforeClass() {
        readerFileExist = new ReaderFileImpl(SOURCE_FILE_GOOD);
        readerFileDoNotExist = new ReaderFileImpl(SOURCE_FILE_BAD);
    }

    @Test
    public void fileExist_Ok() {
        List<String> actualList = readerFileExist.read();
        Assert.assertNotNull(actualList);
    }

    @Test(expected = RuntimeException.class)
    public void fileDoNotExist_NotOk() {
        readerFileDoNotExist.read();
    }
}
