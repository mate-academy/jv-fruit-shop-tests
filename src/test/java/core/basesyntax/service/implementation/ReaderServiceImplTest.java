package core.basesyntax.service.implementation;

import core.basesyntax.service.ReaderService;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ReaderServiceImplTest {
    private final ReaderService readerService = new ReaderServiceImpl();

    @Test
    public void readFromFile_NullFilePath_NotOk() {
        try {
            readerService.readFromFile(null);
        } catch (Exception e) {
            Assert.assertSame(NullPointerException.class, e.getClass());
        }
    }

    @Test
    public void readFromFile_BadFilePath_NotOk() {
        String invalidFilePath = "src/test/resources/fruitInput123.csv";
        try {
            readerService.readFromFile(invalidFilePath);
        } catch (Exception e) {
            Assert.assertSame(RuntimeException.class, e.getClass());
        }
    }

    @Test
    public void readFromFile_BlankFilePath_NotOk() {
        try {
            readerService.readFromFile("");
        } catch (Exception e) {
            Assert.assertSame(RuntimeException.class, e.getClass());
        }
    }

    @Test
    public void readFromFile_correctFilePath() {
        String filePath = "src/test/resources/fruitInput.csv";
        List<String> lines = readerService.readFromFile(filePath);
        Assert.assertEquals(3, lines.size());
        Assert.assertEquals("transaction,fruitType,quantity", lines.get(0));
        Assert.assertEquals("b,banana,20", lines.get(1));
        Assert.assertEquals("b,banana,100", lines.get(2));
    }
}
