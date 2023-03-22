package core.basesyntax.fileservice;

import core.basesyntax.errors.InvalidFileExtensionException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class CsvWriteFileServiceImplTest {
    private WriteFileService csvWriteFileService = new CsvWriteFileServiceImpl();

    @Test(expected = NullPointerException.class)
    public void filePathEqualNull_NotOk() {
        csvWriteFileService.write(null, "a, banana, 30");
    }

    @Test(expected = NullPointerException.class)
    public void contentEqualNull_NotOk() {
        String file = "test.csv";
        csvWriteFileService.write(file, null);
    }

    @Test(expected = InvalidFileExtensionException.class)
    public void checkCorrectFileExtension_NotOk() {
        String file = "test.txt";
        csvWriteFileService.write(file, "a, banana, 30");
    }

    @Test
    public void checkWorkWriteMethod_Ok() {
        String contentForNewFile = "banana,162" + "\n" + "apple,100";
        String expectedFile = "src/test/resource/expectedResult.csv";
        String actualFile = "src/test/resource/actualResult.csv";

        csvWriteFileService.write(actualFile, contentForNewFile);
        List<String> actual = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(actualFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                actual.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<String> expected = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(expectedFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                expected.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void checkCreateFileInWriteMethod() {
        String filePath = "";
        String content = "b, banana, 30";
        csvWriteFileService.write(filePath, content);
    }
}
