package core.basesyntax.service.impl;

import core.basesyntax.service.ContentReader;
import java.io.File;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ContentReaderImplTest {
    private static ContentReader contentReader;
    private static String resourcesPath;

    @BeforeClass
    public static void setUp() {
        contentReader = new ContentReaderImpl();
        resourcesPath = "src" + File.separator + "test" + File.separator
                + "resources" + File.separator;
    }

    @Test
    public void test_empty_read_ok() {
        int expected = 0;
        List<String> listData = contentReader.read(resourcesPath + "empty.txt");
        Assert.assertEquals("Must be equals", expected, listData.size());
    }

    @Test
    public void test_any_file_read_ok() {
        int expected = 3;
        List<String> listData = contentReader.read(resourcesPath + "helloworld.txt");
        Assert.assertEquals("Must be equals", expected, listData.size());
    }
}

