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
    private static final String WRONG_FILE_PATH = "src/test/wrongPath/input.csv";
    private static FileReader fileReader;

    @BeforeClass
    public static void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test(expected = RuntimeException.class)
    public void readFile_wrongPath_notOk() {
        fileReader.readFromFile(WRONG_FILE_PATH);
    }

    @Test
    public void readFile_fileExist_ok() throws IOException {
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
        Files.write(Path.of(FILE_EXIST), expected);
        List<String> actual = fileReader.readFromFile(FILE_EXIST);
        Assert.assertEquals(expected, actual);
    }
}

