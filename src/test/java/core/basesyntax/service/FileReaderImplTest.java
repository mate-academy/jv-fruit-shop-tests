package core.basesyntax.service;

import core.basesyntax.service.impl.FileReaderImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String FILE_EXIST_PATH = "src/test/java/core/basesyntax/resources/input.csv";
    private static final String FILE_NOT_EXIST_PATH = "directory/file.csv";
    private static FileReader fileReader;

    @BeforeClass
    public static void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readFromWrongFile_notOk() {
        fileReader.readFile(FILE_NOT_EXIST_PATH);
    }

    @Test
    public void readFile_Ok() {
        List<String> expectedList = new ArrayList<>();
        expectedList.add("b,banana,20");
        expectedList.add("b,apple,100");
        expectedList.add("s,banana,100");
        expectedList.add("p,banana,13");
        expectedList.add("r,apple,10");
        expectedList.add("p,apple,20");
        expectedList.add("p,banana,5");
        expectedList.add("s,banana,50");
        List<String> actualList = fileReader.readFile(FILE_EXIST_PATH);
        Assert.assertEquals(expectedList, actualList);
    }
}
