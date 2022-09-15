package core.basesyntax.service.impl;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class FileReaderServiceImplTest {
    private final FileReaderServiceImpl fileReaderService = new FileReaderServiceImpl();

    @Test
    public void readFromFile_correctFilePath_Ok() {
        String filePath = "src/test/resources/daily_transactions.csv";
        List<String> transactionList = fileReaderService.read(filePath);
        Assert.assertEquals(9, transactionList.size());
        Assert.assertEquals("type,fruit,quantity", transactionList.get(0));
        Assert.assertEquals("b,banana,20", transactionList.get(1));
        Assert.assertEquals("b,apple,100", transactionList.get(2));
        Assert.assertEquals("s,banana,100", transactionList.get(3));
        Assert.assertEquals("p,banana,13", transactionList.get(4));
        Assert.assertEquals("r,apple,10", transactionList.get(5));
        Assert.assertEquals("p,apple,20", transactionList.get(6));
        Assert.assertEquals("p,banana,5", transactionList.get(7));
        Assert.assertEquals("s,banana,50", transactionList.get(8));
    }

    @Test
    public void readFromFile_nonExistentFile_NotOk() {
        String invalidFilePath = "src/test/resources/nonExistentFile.csv";
        try {
            fileReaderService.read(invalidFilePath);
        } catch (Exception e) {
            Assert.assertSame(RuntimeException.class, e.getClass());
        }
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_nullFile_NotOk() {
        fileReaderService.read(null);
    }

    @Test
    public void readFromFile_EmptyPath_NotOk() {
        try {
            fileReaderService.read("");
        } catch (Exception e) {
            Assert.assertSame(RuntimeException.class, e.getClass());
        }
    }

}