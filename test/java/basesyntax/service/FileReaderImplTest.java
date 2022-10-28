package basesyntax.service;

import core.basesyntax.service.FileReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import core.basesyntax.service.impl.FileReaderImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String FILE_EXIST = "test/resources/input.csv";
    private static final String FILE_NOT_EXIST = "wrong/file.csv";
    private static FileReader fileReaderTest;

    @BeforeClass
    public static void setUp() {
        fileReaderTest = new FileReaderImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readWrongFile_not_Ok() {
        fileReaderTest.readFromFile(FILE_NOT_EXIST);
    }

    @Test
    public void readFile_ok() throws IOException {
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
        String testFileData = "type,fruit,quantity" + System.lineSeparator()
                + "b,banana,20" + System.lineSeparator()
                + "b,apple,100" + System.lineSeparator()
                + "s,banana,100" + System.lineSeparator()
                + "p,banana,13" + System.lineSeparator()
                + "r,apple,10" + System.lineSeparator()
                + "p,apple,20" + System.lineSeparator()
                + "p,banana,5" + System.lineSeparator()
                + "s,banana,50";
        Files.writeString(Path.of(FILE_EXIST), testFileData);
        List<String> actualList = fileReaderTest.readFromFile(FILE_EXIST);
        Assert.assertEquals(expectedList, actualList);
    }
}
