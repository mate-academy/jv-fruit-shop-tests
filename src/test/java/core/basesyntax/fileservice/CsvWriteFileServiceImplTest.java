package core.basesyntax.fileservice;

import core.basesyntax.errors.InvalidFileExtensionException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
        String file = "src/test/java/core/basesyntax/"
                + "resoursefortesting/test.csv";
        csvWriteFileService.write(file, null);
    }

    @Test(expected = InvalidFileExtensionException.class)
    public void checkCorrectFileExtension_NotOk() {
        String file = "src/test/java/core/basesyntax/"
                + "resoursefortesting/test.txt";
        csvWriteFileService.write(file, "a, banana, 30");
    }

    @Test
    public void checkWorkWriteMethod_Ok() {
        String contentForNewFile = "banana,162" + "\n" + "apple,100";
        String actualFile = "src/test/java/core/basesyntax/"
                + "resoursefortesting/ActaulResultFileForTesting.csv";
        String expectedFile = "src/test/java/core/basesyntax/"
                + "resoursefortesting/ExpectedResultFileForTesting.csv";
        csvWriteFileService.write(actualFile, contentForNewFile);
        File expectedFileResource = new File(expectedFile);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(expectedFileResource))) {
            writer.write(contentForNewFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
        File actualFileResource = new File(actualFile);
        actualFileResource.delete();
        expectedFileResource.delete();
    }

    @Test(expected = RuntimeException.class)
    public void checkCreateFileInParseMethod() {
        String filePath = "";
        String content = "b, banana, 30";
        csvWriteFileService.write(filePath, content);
    }
}
