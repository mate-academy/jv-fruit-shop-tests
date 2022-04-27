package core.basesyntax.write.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriteToFileImplTest {
    private static final String OUTPUT_FILE_PATH = "src\\test\\resources\\reportData.csv";
    private static final String INVALID_PATH = " ";
    private static File reportData;
    private static WriteToFile writeToFile;
    private static Map<String,Integer> dataMap;

    @BeforeAll
    static void beforeAll() {
        reportData = new File(OUTPUT_FILE_PATH);
        writeToFile = new WriteToFileImpl();
        dataMap = new HashMap<>();
        try {
            reportData.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Cannot create new file, for path" + OUTPUT_FILE_PATH);
        } finally {
            System.out.println("File was created successfully");
        }
    }

    @Test
    void writer_writeCorrectData_Ok() {
        dataMap.put("banana",152);
        dataMap.put("apple",90);
        writeToFile.write(dataMap,OUTPUT_FILE_PATH);
        String actualResult = readFromFile(OUTPUT_FILE_PATH);
        String expectedResult = "fruit,quantity" + "\r\n"
                + "banana,152" + "\r\n" + "apple,90" + "\r\n";
        assertEquals(expectedResult, actualResult, "Written data is incorrect");
    }

    @Test
    void writer_writeWrongData_0k() {
        dataMap.put("banana",100);
        dataMap.put("apple",88);
        writeToFile.write(dataMap,OUTPUT_FILE_PATH);
        String actualResult = readFromFile(OUTPUT_FILE_PATH);
        String expectedResult = "fruit,quantity" + "\r\n"
                + "banana,152" + "\r\n" + "apple,90" + "\r\n";
        assertNotEquals(expectedResult, actualResult, " ");
    }

    @Test
    void writer_writeToInvalidPath_not0k() {
        assertThrows(RuntimeException.class,()
                -> writeToFile.write(dataMap,INVALID_PATH));
    }

    @Test
    void writer_nullValuePath_not0k() {
        assertThrows(RuntimeException.class,()
                -> writeToFile.write(dataMap,null));
    }

    @AfterAll
    static void clearFiles() {
        try {
            Files.delete(Path.of(OUTPUT_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Cannot clean file" + OUTPUT_FILE_PATH);
        } finally {
            System.out.println("File was deleted successfully");
        }
    }

    private String readFromFile(String filePath) {
        try {
            return Files.readString(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Can't correctly read data from file " + filePath);
        } finally {
            System.out.println("Data was read from file");
        }
    }
}
