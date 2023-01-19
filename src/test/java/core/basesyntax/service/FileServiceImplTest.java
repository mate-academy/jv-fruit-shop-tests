package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.impl.FileServiceImpl;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileServiceImplTest {
    private static final String INPUT_FILE_PATH = "src/test/resources/inputFile.csv";
    private static final String INVALID_FILE_PATH = "null/nofile.file";
    private static final String OUTPUT_FILE_PATH = "src/test/resources/outputFile.csv";
    private static final List<String> TEST_LIST = new ArrayList<>();
    private static FileService fileService;

    @BeforeClass
    public static void beforeClass() {
        fileService = new FileServiceImpl();
        TEST_LIST.add("test1,test2");
        TEST_LIST.add("1,4");
        TEST_LIST.add("2,5");
        TEST_LIST.add("3,6");
    }

    @Before
    public void setUp() {
        
    }

    @Test
    public void readFromFile_read_Ok() {
        List<String> actual = fileService.readFromFile(INPUT_FILE_PATH);
        assertEquals(TEST_LIST, actual);
    }

    @Test
    public void readFromFile_exc_NotOk() {
        try {
            fileService.readFromFile(INVALID_FILE_PATH);
            fail();
        } catch (RuntimeException e) {
            String actualName = e.getClass().getName();
            String expectedName = RuntimeException.class.getName();
            assertEquals(expectedName, actualName);
        }
    }

    @Test
    public void writeToFile_write_Ok() {
        fileService.writeToFile(TEST_LIST, OUTPUT_FILE_PATH);
        try (BufferedReader reader = new BufferedReader(new FileReader(OUTPUT_FILE_PATH))) {
            List<String> actual = reader.lines().collect(Collectors.toList());
            assertEquals(TEST_LIST, actual);
        } catch (IOException e) {
            throw new RuntimeException("Can't find file by path: " + OUTPUT_FILE_PATH, e);
        }
    }

    @Test
    public void writeToFile_exc_NotOk() {
        try {
            fileService.writeToFile(TEST_LIST, INVALID_FILE_PATH);
            fail();
        } catch (RuntimeException e) {
            String actualName = e.getClass().getName();
            String expectedName = RuntimeException.class.getName();
            assertEquals(expectedName, actualName);
        }
    }
}
