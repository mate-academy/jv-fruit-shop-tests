package core.basesyntax.fileservice;

import core.basesyntax.errors.InvalidFileExtensionException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class CsvReadFileServiceImplTest {
    private ReadFileService readFileService = new CsvReadFileServiceImpl();
    private final List<String> listAfterReadingFile = new ArrayList<>();
    
    @Test(expected = InvalidFileExtensionException.class)
    public void checkThrowsExceptionForFileReader_NotOk() {
        String filePath = "test.txt";
        readFileService.read(filePath);
    }

    @Test
    public void checkingTheWorkOfTheReadMethod_Ok() {
        String filePath = "src/test/resource/test.csv";
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
    public void checkReadFileEmptyFileInput_Ok() {
        String filePath = "src/test/resource/emptyFile.csv";
        List<String> actual = readFileService.read(filePath);
        List<String> expected = new ArrayList<>();
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void checkNoSuchFileInput_NotOk() {
        String file = "error.csv";
        readFileService.read(file);
    }

    @Test(expected = NullPointerException.class)
    public void theInputParameterIsNull() {
        readFileService.read(null);
    }
}
