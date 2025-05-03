package mate.academy.service;

import static org.junit.Assert.fail;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import mate.academy.service.impl.CsvFileReaderServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceTest {
    private static final String FILE_NAME = "checkedFile.csv";

    private static final String CORRECT_DATA = "type,fruit,quantity" + System.lineSeparator()
            + "b,banana,20" + System.lineSeparator()
            + "b,apple,100" + System.lineSeparator()
            + "s,banana,100" + System.lineSeparator()
            + "p,banana,13" + System.lineSeparator()
            + "r,apple,10" + System.lineSeparator()
            + "p,apple,20" + System.lineSeparator()
            + "p,banana,5" + System.lineSeparator()
            + "s,banana,50" + System.lineSeparator();

    private static FileReaderService fileReaderService;

    @BeforeClass
    public static void beforeClass() {
        fileReaderService = new CsvFileReaderServiceImpl();
    }

    @After
    public void tearDown() {
        try {
            Files.deleteIfExists(Path.of(FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly clear result files after test ", e);
        }
    }

    @Test
    public void readFromCorrectFile_ok() {
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
        write(CORRECT_DATA, FILE_NAME);
        List<String> actualResult = fileReaderService.readFromFile(FILE_NAME);
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void readFromNonCsvFile_notOk() {
        String nonCsvFileName = "nonCsvFile.html";
        File fromFile = new File(nonCsvFileName);
        write(CORRECT_DATA, nonCsvFileName);
        try {
            fileReaderService.readFromFile(nonCsvFileName);
        } catch (RuntimeException e) {
            try {
                Files.deleteIfExists(Path.of(nonCsvFileName));
            } catch (IOException ex) {
                throw new RuntimeException("Can't correctly clear result files after test "
                        + nonCsvFileName, ex);
            }
            return;
        }
        fail("You must throw exception if file not .csv");
    }

    @Test
    public void readFromEmptyFile_notOk() {
        write("",FILE_NAME);
        try {
            fileReaderService.readFromFile(FILE_NAME);
        } catch (RuntimeException e) {
            return;
        }
        fail("You must throw exception if file is empty");
    }

    public void write(String resultString, String toFileName) {
        File toFile = new File(toFileName);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(toFile))) {
            bufferedWriter.write(resultString);
        } catch (IOException e) {
            throw new RuntimeException("Can't write data to the file" + toFileName, e);
        }
    }

}
