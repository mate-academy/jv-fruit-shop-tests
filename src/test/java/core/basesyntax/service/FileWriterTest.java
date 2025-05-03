package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.service.impl.FileReaderServiceImpl;
import core.basesyntax.service.impl.FileWriterImpl;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterTest {
    private static final String TEST_PATH = "src/main/resources/dayreport.csv";
    private static final String FILE_HEADER = "fruit,quantity";
    private static List<String> testList = new ArrayList<>();
    private static FileWriter fileWriter;

    @BeforeClass
    public static void beforeClass() throws Exception {
        testList.add(FILE_HEADER);
        testList.add(System.lineSeparator());
        testList.add("banana,30");
        testList.add(System.lineSeparator());
        testList.add("pineapple,22");
        testList.add(System.lineSeparator());
        testList.add("apple,50");
    }

    @Before
    public void setUp() throws Exception {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void writeToFileFileExist_OK() {
        fileWriter.writeToFile(TEST_PATH, testList);
        File reportFile = new File(TEST_PATH);
        assertTrue(reportFile.exists());
    }

    @Test
    public void writeToFile_OK() {
        File file = new File(TEST_PATH);
        file.delete();
        fileWriter.writeToFile(TEST_PATH, testList);
        FileReaderService fileReaderService = new FileReaderServiceImpl();
        List<String> listFromFile = fileReaderService.readFromFile(TEST_PATH);
        testList.removeAll(Collections.singleton(System.lineSeparator()));
        assertEquals(testList, listFromFile);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_nullList_NotOK() {
        fileWriter.writeToFile(TEST_PATH, null);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_emptyList_NotOK() {
        List<String> anotherTestList = new ArrayList<>();
        fileWriter.writeToFile(TEST_PATH, anotherTestList);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_testListContainsNull_NotOK() {
        List<String> anotherTestList = new ArrayList<>();
        anotherTestList.add(null);
        fileWriter.writeToFile(TEST_PATH, anotherTestList);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_filePath_null_NotOK() {
        fileWriter.writeToFile(null, testList);
    }
}
