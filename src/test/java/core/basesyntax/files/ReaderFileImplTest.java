package core.basesyntax.files;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ReaderFileImplTest {
    private static final String SOURCE_FILE_GOOD = "src/main/resources/12-09-2021.csv";
    private static final String SOURCE_FILE_BAD = "src/main/resources/sderfdsde.csv";
    private Reader readerFileExist = new ReaderFileImpl(SOURCE_FILE_GOOD);
    private Reader readerFileDoNotExist = new ReaderFileImpl(SOURCE_FILE_BAD);

    @Test
    public void fileExist_Ok() {
        List<String> actualList = readerFileExist.read();
        Assert.assertTrue(actualList != null);
    }

    @Test(expected = RuntimeException.class)
    public void fileDoNotExist_NotOk() {
        readerFileDoNotExist.read();
    }
}
