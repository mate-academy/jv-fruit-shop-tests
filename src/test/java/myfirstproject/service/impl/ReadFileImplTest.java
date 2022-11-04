package myfirstproject.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import myfirstproject.service.ReadFile;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReadFileImplTest {
    private static final String FILE_EXIST = "src/test/resources/sourceFile.csv";
    private static final String FILE_NOT_EXIST = "wrong/file.csv";
    private static ReadFile fileReaderTest;
    private static final List<String[]> temporalList = new ArrayList<>();

    @BeforeClass
    public static void setUp() {
        fileReaderTest = new ReadFileImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readWrongFile_not_Ok() {
        fileReaderTest.readFile(temporalList, FILE_NOT_EXIST);
    }

    @Test
    public void readFile_ok() {
        List<String[]> expectedList = new ArrayList<>();
        StringBuilder expectedBuilder = new StringBuilder();
        StringBuilder actualBuilder = new StringBuilder();

        expectedList.add("type,fruit,quantity".split(","));
        expectedList.add("b,banana,20".split(","));
        expectedList.add("b,apple,100".split(","));
        expectedList.add("s,banana,100".split(","));
        expectedList.add("p,banana,13".split(","));
        expectedList.add("r,apple,10".split(","));
        expectedList.add("p,apple,20".split(","));
        expectedList.add("p,banana,5".split(","));
        expectedList.add("s,banana,50".split(","));

        fileReaderTest.readFile(temporalList,FILE_EXIST);
        for (String[] s : expectedList) {
            expectedBuilder.append(Arrays.toString(s));
            expectedBuilder.append(System.lineSeparator());
        }
        for (String[] s : temporalList) {
            actualBuilder.append(Arrays.toString(s));
            actualBuilder.append(System.lineSeparator());
        }

        String expected = expectedBuilder.toString();
        String actual = actualBuilder.toString();
        Assert.assertEquals(actual, expected);
    }
}
