package service.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import service.FileService;

public class FileServiceImplTest {
    private static FileService fileService;
    private static final String VALID_INPUT_PATH = "src/test/java/resources/input.csv";
    private static final String INVALID_INPUT_PATH = "src/resources/input.csv";
    private static final String VALID_OUTPUT_PATH = "src/test/java/resources/test.csv";

    @BeforeClass
    public static void fileServiceInit() {
        fileService = new FileServiceImpl();
    }

    @Test
    public void readFromCsvFile_Ok() {
        List<String> expected = List.of("b,banana,20");
        List<String> actual = fileService.readFromCsvFile(VALID_INPUT_PATH);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromCsvFileWithInvalidPath_RuntimeException() {
        fileService.readFromCsvFile(INVALID_INPUT_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void writeToCsvFile_Ok() {
        String report = "banana,20" + System.lineSeparator() + "apple,30";
        List<String> expected = List.of("banana,20", "apple,30");
        fileService.writeToCsvFile(report, VALID_OUTPUT_PATH);
        List<String> actual = fileService.readFromCsvFile(VALID_OUTPUT_PATH);
        assertEquals(expected, actual);
    }
}
