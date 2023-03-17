package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriterService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {

    private static final String FILE_PATH = "src/main/java/resources/report.csv";
    private static List<String> expectedData;
    private static WriterService writerService;

    @BeforeClass
    public static void beforeAll() {
        writerService = new WriterServiceImpl();
        expectedData = new ArrayList<>();
        expectedData.add("fruit,quantity");
        expectedData.add("banana,152");
        expectedData.add("apple,90");
    }

    @Test
    public void writeToFile_ok() {
        writerService.writeToFile(FILE_PATH, expectedData);
        List<String> actualData = new ArrayList<>();
        File file = new File(FILE_PATH);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String string = bufferedReader.readLine();
            while (string != null) {
                actualData.add(string);
                string = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Can't find file. " + e);
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file. " + e);
        }
        assertEquals(expectedData,actualData);
    }
}
