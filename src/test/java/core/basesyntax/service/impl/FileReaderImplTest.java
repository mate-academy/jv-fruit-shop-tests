package core.basesyntax.service.impl;

import core.basesyntax.service.FileReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static FileReader fileReader;
    private List<String> reportList;
    private File file;

    @BeforeClass
    public static void beforeAll() {
        fileReader = new FileReaderImpl();
    }

    @Before
    public void create() {
        reportList = new ArrayList<>();
        reportList.add("type,fruit,quantity");
        reportList.add("b,mango,80");
        reportList.add("b,pineapple,30");
        reportList.add("p,mango,20");
        reportList.add("p,pineapple,5");
        reportList.add("s,mango,40");
        reportList.add("s,pineapple,10");
        file = new File("src/test/resources/inputTest.csv");
    }

    @Test
    public void service_fileReaderRead_ok() {
        List<String> actual = fileReader.read(file);
        List<String> expected = reportList;
        Assert.assertEquals(expected,actual);
    }

    @Test(expected = RuntimeException.class)
    public void service_fileReaderReadMissingFile_notOk() {
        file = new File("src/test/resources/missingFile.csv");
        fileReader.read(file);
    }

    @Test
    public void service_fileReaderReadEmpty_notOk() {
        file = new File("src/test/resources/emptyInputTest.csv");
        List<String> actual = fileReader.read(file);
        Exception expectedException = new RuntimeException();
        Assert.assertNotSame(expectedException,actual);
    }

    @After
    public void clear() {
        reportList.clear();
    }
}
