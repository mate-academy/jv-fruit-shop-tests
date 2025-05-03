package core.basesyntax.service.impl;

import core.basesyntax.service.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String FILE_EXIST = "src/main/resourses/input.csv";
    private static final String FILE_NOT_EXIST = "any/wrong_file.csv";
    private static FileReader fileReaderTest;

    @BeforeClass
    public static void setUp() {
        fileReaderTest = new FileReaderImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readWrongFile_Not_Ok() {
        fileReaderTest.readFromFile(FILE_NOT_EXIST);
    }

    @Test
    public void readFile_Ok() {
        List<String> expectedList = new ArrayList<>();
        expectedList.add("type,fruit,quantity");
        expectedList.add("b,banana,20");
        expectedList.add("b,apple,100");
        expectedList.add("s,banana,100");
        expectedList.add("p,banana,13");
        expectedList.add("r,apple,10");
        expectedList.add("p,apple,20");
        expectedList.add("p,banana,5");
        expectedList.add("s,banana,50");
        List<String> actualList = fileReaderTest.readFromFile(FILE_EXIST);
        Assert.assertEquals(expectedList, actualList);
    }
}
