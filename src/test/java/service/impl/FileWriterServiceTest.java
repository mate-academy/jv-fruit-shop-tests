package service.impl;

import static org.junit.Assert.assertArrayEquals;

import au.com.bytecode.opencsv.CSVReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import service.FileWriterService;

public class FileWriterServiceTest {
    private static FileWriterService fileWriterService;
    private static final String[][] STENCIL = {{"banana", "250"}, {"apple", "190"}};
    private static final String FILE_PATH = "src/test/resources/writingTest.csv";

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void initial() {
        fileWriterService = new FileWriterServiceImpl();
    }

    @Test
    public void writeFile_filePathCorrect_ok() {
        String message = "original data and data from file are differed";
        fileWriterService.writeFile(FILE_PATH, Arrays.asList(STENCIL));
        List<String[]> list = new ArrayList<>();
        File file = new File(FILE_PATH);
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            list.addAll(reader.readAll());
        } catch (IOException e) {
            throw new RuntimeException("Cannot read file: " + file.getName() + e);
        }
        assertArrayEquals(message, STENCIL[0], list.get(0));
        assertArrayEquals(message, STENCIL[1], list.get(1));
    }

    @Test
    public void writeFile_filePathNotCorrect_notOk() {
        String wrongPath = "src/java/resources/writingTest.csv";
        exception.expect(RuntimeException.class);
        exception.expectMessage("Can't write data to file");
        fileWriterService.writeFile(wrongPath, Arrays.asList(STENCIL));
    }
}
