package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.FileReaderService;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String filePathNameOutput = "src/main/resources/dataOutputTest.csv";
    private static final String inputData = "fruit,quantity\n"
            +
            "banana,152\n"
            +
            "apple,90\n";
    private static FileWriterService fileWriterService;
    private static FileReaderService readerService;

    @BeforeClass
    public static void setUp() {
        fileWriterService = new FileWriterImpl();
        readerService = new FileReaderServiceImpl();
    }

    @Test
    public void writeDataToFile_identicalcontent_Ok() {
        fileWriterService.writeDataToFile(inputData, filePathNameOutput);
        File file = new File(filePathNameOutput);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file));) {
            String value = reader.readLine();
            while (value != null) {
                builder.append(value).append(System.lineSeparator());
                value = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Can't open the file: " + filePathNameOutput, e);
        } catch (IOException e) {
            throw new RuntimeException("Can't read the date from file: " + filePathNameOutput, e);
        }
        String actual = builder.toString();
        assertEquals(inputData, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void writeDataToFile_path_null_ok() {
        fileWriterService.writeDataToFile(null, filePathNameOutput);
    }

    @AfterClass
    public static void afterClass() {
        File file = new File(filePathNameOutput);
        file.delete();
    }
}
