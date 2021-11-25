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
    public void read_not_existed_file_notOK() {
        reader.read("pew");
    }

    @Test
    public void csvReader_read_OK() {
        List<String> read = reader.read("src\\test\\resources\\banana.csv");
        Assert.assertTrue(read.contains("banana"));
    }
}
