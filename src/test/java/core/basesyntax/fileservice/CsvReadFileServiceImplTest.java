package core.basesyntax.fileservice;

import core.basesyntax.errors.InvalidFileExtensionException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class CsvReadFileServiceImplTest {
    private ReadFileService readFileService = new CsvReadFileServiceImpl();
    private final List<String> listAfterReadingFile = new ArrayList<>();
    
    @Test(expected = InvalidFileExtensionException.class)
    public void checkThrowsExceptionForFileReader_NotOk() {
        String filePath = "src/test/java/core/basesyntax/"
                + "resoursefortesting/test.txt";
        readFileService.read(filePath);
    }

    @Test
    public void checkingTheWorkOfTheReadMethod_Ok() {
        String filePath = "src/test/java/core/basesyntax/"
                + "resoursefortesting/test.csv";
        String contentForExpectedFile = "b,banana,30\n"
                + "b,apple,110\n"
                + "s,banana,110\n"
                + "p,banana,23\n"
                + "r,apple,20\n"
                + "p,apple,30\n"
                + "p,banana,15\n"
                + "s,banana,60";
        File file = new File(filePath);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Can't create file", e);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(contentForExpectedFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
        file.delete();
    }

    @Test
    public void checkReadFileEmptyFileInput_Ok() {
        String filePath = "src/test/java/core/basesyntax/"
                + "resoursefortesting/emptyFile.csv";
        File file = new File(filePath);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Can't create file", e);
        }
        List<String> actual = readFileService.read(filePath);
        List<String> expected = new ArrayList<>();
        Assert.assertEquals(expected, actual);
        file.delete();
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
