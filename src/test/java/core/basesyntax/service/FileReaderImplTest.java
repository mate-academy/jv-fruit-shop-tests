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
    private static final String FILE_EXIST = "src/test/java/core/basesyntax/resources/input.csv";
    private static final String FILE_NOT_EXIST = "directory/file.csv";
    private static FileReader fileReaderTest;

    @BeforeClass
    public static void setUp() {
        fileReaderTest = new FileReaderImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readFromWrongFile_notOk() {
        fileReaderTest.readFile(FILE_NOT_EXIST);
    }

    @Test
    public void readFile_Ok() throws IOException {
        List<String> list = new ArrayList<>();
        list.add("b,banana,20");
        list.add("b,apple,100");
        list.add("s,banana,100");
        list.add("p,banana,13");
        list.add("r,apple,10");
        list.add("p,apple,20");
        list.add("p,banana,5");
        list.add("s,banana,50");
        String inputData = "b,banana,20" + System.lineSeparator()
                + "b,apple,100" + System.lineSeparator()
                + "s,banana,100" + System.lineSeparator()
                + "p,banana,13" + System.lineSeparator()
                + "r,apple,10" + System.lineSeparator()
                + "p,apple,20" + System.lineSeparator()
                + "p,banana,5" + System.lineSeparator()
                + "s,banana,50";
        Files.writeString(Path.of(FILE_EXIST), inputData);
        List<String> actualList = fileReaderTest.readFile(FILE_EXIST);
        Assert.assertEquals(list, actualList);
    }
}
