package core.basesyntax.service.file;

import core.basesyntax.service.file.impl.FileReaderImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderTest {
    private static final String PATH_TO_FILE_INPUT = "src/main/resources/data.csv";
    private static final String PATH_TO_EMPTY_FILE = "src/main/resources/empty.csv";
    private static final String PATH_TO_NOT_EXISTING_FILE = "src/main/resources/file.csv";
    private static FileReader fileReader;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void readFile_isOk() {
        List<String> exceptedRecords = List.of(
                "type,fruit,quantity", "b,banana,20", "b,apple,100",
                "s,banana,100", "p,banana,13", "r,apple,10",
                "p,apple,20", "p,banana,5", "s,banana,50");
        List<String> actualRecords = fileReader.read(PATH_TO_FILE_INPUT);
        Assert.assertEquals(exceptedRecords, actualRecords);
    }

    @Test
    public void readFromEmptyFile_isOK() {
        List<String> exceptedRecords = new ArrayList<>();
        List<String> actualRecords = fileReader.read(PATH_TO_EMPTY_FILE);
        Assert.assertEquals(exceptedRecords, actualRecords);
    }

    @Test(expected = RuntimeException.class)
    public void readFromNotExistFile_notOk() {
        fileReader.read(PATH_TO_NOT_EXISTING_FILE);
    }
}
