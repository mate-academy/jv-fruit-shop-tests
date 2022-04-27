package core.basesyntax.write.file;

import core.basesyntax.database.Storage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriteToFileImplTest {
    private static final String OUTPUT_FILE_PATH = "src/test/resources/reportData.csv";
    private static final String INVALID_PATH = "src/test/";
    private static File reportData;
    private static WriteToFile writeToFile;

    @BeforeClass
    public static void beforeAll() {
        reportData = new File(OUTPUT_FILE_PATH);
        writeToFile = new WriteToFileImpl();
        try {
            reportData.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Cannot create new file, for path " + OUTPUT_FILE_PATH);
        } finally {
            System.out.println("File was created successfully.");
        }
    }

    @Test
    public void write_passCorrectData_Ok() {
        Storage.fruitTransactionStorage.put("banana",152);
        Storage.fruitTransactionStorage.put("apple",90);
        writeToFile.write(Storage.fruitTransactionStorage,OUTPUT_FILE_PATH);
        String actualResult = readFromFile(OUTPUT_FILE_PATH);
        String expectedResult = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator() + "apple,90" + System.lineSeparator();
        Assert.assertEquals("Written data to the file is incorrect.",
                expectedResult, actualResult);
    }

    @Test(expected = RuntimeException.class)
    public void write_passToInvalidPath_not0k() {
        writeToFile.write(Storage.fruitTransactionStorage,INVALID_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void write_nullValuePath_not0k() {
        writeToFile.write(Storage.fruitTransactionStorage,null);
    }

    @After
    public void afterEach() {
        Storage.fruitTransactionStorage.clear();
    }

    private String readFromFile(String filePath) {
        try {
            return Files.readString(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly read data from file " + filePath);
        }
    }
}
