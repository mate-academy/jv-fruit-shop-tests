package core.basesyntax.sevice;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriteServiceImplTest {
    private static FileWriteService fileWriteService;
    private static String testText;
    private static final String PATH_TO_FILE = "src/test/resourses_tests/Test3_WriteToFile.csv";
    private static File tempFile;

    @BeforeClass
    public static void beforeClass() {
        fileWriteService = new FileWriteServiceImpl();
        testText = "Test line one.\r\nTest line two.\r\nTest line three.";
        tempFile = new File(PATH_TO_FILE);
    }

    @Test
    public void writeDataToFile_validData_Ok() {
        fileWriteService.writeDataToFile(testText, PATH_TO_FILE);
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(tempFile))) {
            int value = bufferedReader.read();
            while (value != -1) {
                stringBuilder.append((char) value);
                value = bufferedReader.read();
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't get data!", e);
        }
        assertEquals(testText, stringBuilder.toString());
    }

    @AfterClass
    public static void afterClass() {
        tempFile.delete();
    }
}
