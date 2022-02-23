package core.basesyntax.service;

import core.basesyntax.service.impl.FileReaderService;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.junit.AfterClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String filePathNameOutput = "src/main/resources/dataOutputTest.csv";
    private static final String inputData = "fruit,quantity\n"
            +
            "banana,152\n"
            +
            "apple,90\n";
    private FileWriterService fileWriterService;
    private FileReaderService readerService;

    @BeforeEach
    void setUp() {
        fileWriterService = new FileWriterImpl();
        readerService = new FileReaderServiceImpl();
    }

    @Test
    void writingIdenticalContentIntoFile_Ok() {
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
        Assertions.assertEquals(inputData, actual);
    }

    @Test
    public void writeToFilePathNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            fileWriterService.writeDataToFile(null, filePathNameOutput);
        });
    }

    @AfterClass
    public static void afterClass() {
        File file = new File(filePathNameOutput);
        file.delete();
    }
}
