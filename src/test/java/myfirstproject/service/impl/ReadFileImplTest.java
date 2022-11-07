package myfirstproject.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import myfirstproject.service.ReadFile;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReadFileImplTest {
    private static final String PATH_TO_NEW_FILE = "src/test/resources/sourceFile.csv";
    private static final String FILE_NOT_EXIST = "wrong/file.csv";
    private static ReadFile fileReaderTest;
    private static final List<String[]> temporalList = new ArrayList<>();

    @BeforeClass
    public static void before() {
        fileReaderTest = new ReadFileImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readWrongFile_notOk() {
        fileReaderTest.readFile(temporalList, FILE_NOT_EXIST);
    }

    @Test
    public void readFile_Ok() {
        StringBuilder actualBuilder = new StringBuilder();
        String expected = "[type, fruit, quantity]" + System.lineSeparator()
                + "[b, banana, 20]" + System.lineSeparator()
                + "[b, apple, 100]" + System.lineSeparator()
                + "[s, banana, 100]" + System.lineSeparator()
                + "[p, banana, 13]" + System.lineSeparator()
                + "[r, apple, 10]" + System.lineSeparator()
                + "[p, apple, 20]" + System.lineSeparator()
                + "[p, banana, 5]" + System.lineSeparator()
                + "[s, banana, 50]" + System.lineSeparator();

        fileReaderTest.readFile(temporalList, PATH_TO_NEW_FILE);
        for (String[] s : temporalList) {
            actualBuilder.append(Arrays.toString(s));
            actualBuilder.append(System.lineSeparator());
        }

        String actual = actualBuilder.toString();
        Assert.assertEquals(actual, expected);
    }
}
