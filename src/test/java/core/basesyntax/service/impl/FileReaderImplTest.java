package core.basesyntax.service.impl;

import core.basesyntax.service.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String FILE_NAME_TEST = "src/test/resources/input_file_test.csv";
    private static FileReader fileReader;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void readInputFile_CorrectFormatFile_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
        List<String> actual = fileReader.readInputFile(FILE_NAME_TEST);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readInputFile_IncorrectNameFile_NotOk() {
        String fileName = "incorrectNameFile";
        fileReader.readInputFile(fileName);
    }

    @Test(expected = RuntimeException.class)
    public void readInputFile_Null_NotOk() {
        String fileName = null;
        fileReader.readInputFile(fileName);
    }
}
