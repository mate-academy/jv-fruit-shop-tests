package core.basesyntax;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.impl.FileReaderImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String NOT_EXISTING_FILE_PATH = "src/test/resources/newInformation.csv";
    private static final String EMPTY_FILE_PATH = "src/test/resources/emptyFile.csv";
    private static final String INVALID_FILE_PATH = "src/test/resources/partlyValidInformation.csv";
    private static final String VALID_FILE_PATH = "src/test/resources/validInformation.csv";
    private static FileReader fileReader;

    @BeforeClass
    public static void initialization() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void readFromFile_validFile_ok() {
        List<String> expected = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
        List<String> actual = fileReader.readFromFile(VALID_FILE_PATH);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_emptyFile_ok() {
        List<String> expected = new ArrayList<>();
        List<String> actual = fileReader.readFromFile(EMPTY_FILE_PATH);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void readFromFile_invalidFile_ok() {
        List<String> expected = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "k,apple,10",
                "p,apple,**");
        List<String> actual = fileReader.readFromFile(INVALID_FILE_PATH);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_notExistingFile_notOk() {
        fileReader.readFromFile(NOT_EXISTING_FILE_PATH);
    }
}
