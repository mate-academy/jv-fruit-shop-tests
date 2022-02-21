package fruite.store.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

public class FileWriterServiceImplTest {
    private static final String VALID_FILE_NAME_AND_PATH =
            "src/test/resources/report-by-day.csv";
    private static final String INVALID_FILE_NAME_OR_PATH
            = "src/tst/resorces/report-by-day.csv";
    private FileWriterServiceImpl fileWriterService = new FileWriterServiceImpl();

    @AfterClass
    public static void afterClass() {
        try {
            Files.deleteIfExists(Path.of(VALID_FILE_NAME_AND_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly clear result files after test: ", e);
        }
    }

    @Test
    public void writeToFile_validPathOrFileName_ok() {
        String expectedResult = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90";
        byte[] expectedResultInBytes = expectedResult.getBytes();
        fileWriterService.writeToFile(expectedResultInBytes, VALID_FILE_NAME_AND_PATH);
        List<String> lines;
        try {
            lines = Files.readAllLines(Path.of(VALID_FILE_NAME_AND_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file: " + VALID_FILE_NAME_AND_PATH);
        }
        String actual = lines.stream()
                .collect(Collectors.joining(System.lineSeparator()));
        Assert.assertEquals(expectedResult, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_invalidPath_notOk() {
        String expectedResult = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();
        byte[] expectedResultInBytes = expectedResult.getBytes();
        fileWriterService.writeToFile(expectedResultInBytes, INVALID_FILE_NAME_OR_PATH);
    }
}
