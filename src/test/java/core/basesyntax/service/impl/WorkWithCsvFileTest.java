package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WorkWithCsvFileTest {
    private static final String BASE_INPUT_FILENAME = "src/test/resources/base_input.csv";
    private static final String BASE_OUTPUT_FILENAME = "src/test/resources/base_output_test.csv";
    private static WorkWithCsvFile workWithCsvFiles;

    @BeforeClass
    public static void beforeClass() {
        workWithCsvFiles = new WorkWithCsvFile();
    }

    @Test
    public void readFromFile_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add("type,fruit,quantity");
        expected.add("b,banana,20");
        expected.add("b,apple,100");
        expected.add("s,banana,100");
        expected.add("p,banana,13");
        expected.add("r,apple,10");
        expected.add("p,apple,20");
        expected.add("p,banana,5");
        expected.add("s,banana,50");
        List<String> actual = workWithCsvFiles.readFromFile(BASE_INPUT_FILENAME);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readFromFile_invalidFilename_notOk() {
        workWithCsvFiles.readFromFile(null);
    }

    @Test
    public void writeToFile_Ok() {
        String expected = "Correct,writing,to,file";
        workWithCsvFiles.writeToFile(BASE_OUTPUT_FILENAME, "Correct,writing,to,file");
        File file = new File(BASE_OUTPUT_FILENAME);
        String actual;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            actual = reader.readLine();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Can't find file with file name " + BASE_OUTPUT_FILENAME, e);
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file " + BASE_OUTPUT_FILENAME, e);
        }
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_invalidFileName_notOk() {
        workWithCsvFiles.writeToFile(null, "content");
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_invalidContent_notOk() {
        workWithCsvFiles.writeToFile("output.csv", null);
    }
}
