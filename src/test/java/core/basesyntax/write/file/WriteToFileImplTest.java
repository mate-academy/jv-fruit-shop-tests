package core.basesyntax.write.file;

import core.basesyntax.database.Storage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class WriteToFileImplTest {
    private static final String OUTPUT_FILE_PATH = "src\\test\\resources\\reportData.csv";
    private static final String INVALID_PATH = " ";
    private static File reportData;
    private static WriteToFile writeToFile;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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
    public void writer_writeCorrectData_Ok() {
        Storage.fruitTransactionStorage.put("banana",152);
        Storage.fruitTransactionStorage.put("apple",90);
        writeToFile.write(Storage.fruitTransactionStorage,OUTPUT_FILE_PATH);
        String actualResult = readFromFile(OUTPUT_FILE_PATH);
        String expectedResult = "fruit,quantity" + "\r\n"
                + "banana,152" + "\r\n" + "apple,90" + "\r\n";
        Assert.assertEquals("Written data to the file is incorrect.",
                expectedResult, actualResult);
    }

    @Test
    public void writer_writeToInvalidPath_not0k() {
        thrown.expect(RuntimeException.class);
        writeToFile.write(Storage.fruitTransactionStorage,INVALID_PATH);
    }

    @Test
    public void writer_nullValuePath_not0k() {
        thrown.expect(RuntimeException.class);
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
        } finally {
            System.out.println("Data was read from file.");
        }
    }
}
