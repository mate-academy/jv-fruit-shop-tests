package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileReaderService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CsvFileReaderServiceImplTest {
    private static final FileReaderService fileReaderService = new CsvFileReaderServiceImpl();
    private static final String EMPTY_STRING = "";
    private static List<String> expectedResult;
    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setUp() {
        expectedResult = new ArrayList<>(List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50",
                "s,orange,100",
                "r,melon,20"));
    }

    @After
    public void tearDown() {
        expectedResult.clear();
    }

    @Test
    public void readFromFile_nullFilePath_NotOk() {
        exceptionRule.expect(NullPointerException.class);
        exceptionRule.expectMessage("Filepath cannot be null");
        fileReaderService.readFromFile(null);
    }

    @Test
    public void readFromFile_noExistFilePath_NotOk() {
        String filePath = "noExistFile.csv";
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Can't get data from file: " + filePath);
        fileReaderService.readFromFile(filePath);
    }

    @Test
    public void readFromFile_validInputFile_Ok() {
        List<String> actualResult = fileReaderService.readFromFile(
                "src/test/resources/inputValidData.csv");
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void readFromFile_emptyInputFile_Ok() {
        List<String> actualResult = fileReaderService.readFromFile(
                "src/test/resources/inputEmptyFile.csv");
        assertEquals(Collections.emptyList(), actualResult);
    }

    @Test
    public void readFromFile_emptyRowsInInputFile_Ok() {
        expectedResult.add(0, EMPTY_STRING);
        expectedResult.add(2, EMPTY_STRING);
        expectedResult.add(EMPTY_STRING);
        List<String> actualResult = fileReaderService.readFromFile(
                "src/test/resources/inputEmptyRows.csv");
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void readFromFile_emptyFileName_Ok() {
        List<String> actualResult = fileReaderService.readFromFile(
                "src/test/resources/.csv");
        assertEquals(expectedResult, actualResult);
    }
}
