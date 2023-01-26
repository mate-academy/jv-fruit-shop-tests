package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class FileServiceImplTest {
    private static final String STORE_FILE_PATH = "src/main/resources/transactions.csv";
    private static final String WRITING_RESULT_FILE_PATH = "forwriting.csv";
    private static final String NOT_EXIST_FILE_PATH = "notexist.csv";
    private FileService fileService = new FileServiceImpl();

    @Test
    public void readingDataFromFile_Ok() {
        List<String> expectedResult = new ArrayList<>();
        expectedResult.add("type,fruit,quantity");
        expectedResult.add("b,banana,20");
        expectedResult.add("b,apple,100");
        expectedResult.add("s,banana,100");
        expectedResult.add("p,banana,13");
        expectedResult.add("r,apple,10");
        expectedResult.add("p,apple,20");
        expectedResult.add("p,banana,5");
        expectedResult.add("s,banana,50");
        List<String> actualResult = fileService.readDataFromFile(STORE_FILE_PATH);
        assertEquals(expectedResult, actualResult);
    }

    @Test(expected = RuntimeException.class)
    public void readingFromInvalidPathFile_NotOk() {
        fileService.readDataFromFile(NOT_EXIST_FILE_PATH);
    }

    @Test
    public void writeDataToFile_Ok() {
        List<String> expectedList = new ArrayList<>();
        expectedList.add("type,fruit,quantity");
        expectedList.add("b,banana,20");
        expectedList.add("b,apple,100");
        String writingData = "type,fruit,quantity, b,banana,20, b,apple,100";
        fileService.writeDataToFile(WRITING_RESULT_FILE_PATH, writingData);
        String actualResult = fileService.readDataFromFile(WRITING_RESULT_FILE_PATH).toString();
        String expectedResult = expectedList.toString();
        assertEquals(expectedResult, actualResult);
        try {
            Files.deleteIfExists(Path.of(WRITING_RESULT_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly clear result files after test ", e);
        }
    }
}
