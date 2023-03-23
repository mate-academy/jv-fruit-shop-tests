package core.basesyntax.fileservice;

import core.basesyntax.errors.InvalidFileExtensionException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CsvReadFileServiceImplTest {
    private ReadFileService readFileService;
    private final List<String> listAfterReadingFile = new ArrayList<>();

    @Before
    public void initializationOfVariables() {
        readFileService = new CsvReadFileServiceImpl();
    }

    @Test(expected = InvalidFileExtensionException.class)
    public void read_incorrectFileExtension_notOk() {
        String filePath = "test.txt";
        readFileService.read(filePath);
    }

    @Test
    public void read_checkWork_ok() {
        String filePath = "src/test/resources/test.csv";
        listAfterReadingFile.add("b,banana,30");
        listAfterReadingFile.add("b,apple,110");
        listAfterReadingFile.add("s,banana,110");
        listAfterReadingFile.add("p,banana,23");
        listAfterReadingFile.add("r,apple,20");
        listAfterReadingFile.add("p,apple,30");
        listAfterReadingFile.add("p,banana,15");
        listAfterReadingFile.add("s,banana,60");
        List<String> actual = readFileService.read(filePath);
        Assert.assertEquals(listAfterReadingFile, actual);
    }

    @Test
    public void read_inputFileIsEmpty_ok() {
        String filePath = "src/test/resources/emptyFile.csv";
        List<String> actual = readFileService.read(filePath);
        List<String> expected = new ArrayList<>();
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_wrongFileInput_notOk() {
        String file = "error.csv";
        readFileService.read(file);
    }
}
