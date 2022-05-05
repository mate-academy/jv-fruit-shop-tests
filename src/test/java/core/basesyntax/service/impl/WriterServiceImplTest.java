package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.WriterService;
import core.basesyntax.storage.Storage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    public static final String OUTPUT_FILE_PATH = "src/test/resources/output.csv";
    private static WriterService writerService;

    @BeforeClass
    public static void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Before
    public void initializeStorage() {
        Storage.fruits.put(new Fruit("banana"), 100);
        Storage.fruits.put(new Fruit("apple"), 100);
    }

    @Test
    public void writeToFile_ok() {
        String textForWriting = "fruit,quantity"
                + System.lineSeparator()
                + "banana,100"
                + System.lineSeparator()
                + "apple,100";
        writerService.writeToFile(OUTPUT_FILE_PATH, textForWriting);
        List<String> expected = new ArrayList<>();
        expected.add("fruit,quantity");
        expected.add("banana,100");
        expected.add("apple,100");
        List<String> actual = readFromFile(OUTPUT_FILE_PATH);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_filePathNotOk() {
        String textForWriting = "fruit,quantity"
                + System.lineSeparator()
                + "banana,100"
                + System.lineSeparator()
                + "apple,100";
        writerService.writeToFile("folder/incorrect/File/Path", textForWriting);
    }

    @After
    public void clearStorage() {
        Storage.fruits.clear();
    }

    private List<String> readFromFile(String fileName) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            List<String> stringsList = new ArrayList<>();
            String reader = bufferedReader.readLine();
            while (reader != null) {
                stringsList.add(reader);
                reader = bufferedReader.readLine();
            }
            return stringsList;
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file" + fileName, e);
        }
    }
}
