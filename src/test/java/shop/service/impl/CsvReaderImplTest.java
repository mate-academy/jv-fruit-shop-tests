package shop.service.impl;

import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import shop.service.Reader;

public class CsvReaderImplTest {
    private static Reader reader;

    @BeforeClass
    public static void beforeAll() {
        reader = new CsvReaderImpl();
    }

    @Test(expected = RuntimeException.class)
    public void csvReader_not_existed_file_notOK() {
        reader.read("pew");
    }

    @Test
    public void csvReader_read_OK() {
        List<String> read = reader.read("src/test/resources/banana.csv");
        Assert.assertEquals("banana", read.get(0));
    }

    @Test
    public void csvReader_read_emptyFile_OK() {
        List<String> read = reader.read("src/test/resources/empty.csv");
        Assert.assertTrue(read.isEmpty());
    }
}
