package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exceptions.WriteNotPossibleException;
import core.basesyntax.service.FileWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String PATH_TO_FILE = "src/test/resources/OutputTest.csv";
    private static final String WRONG_PATH_TO_FILE = "src/test/resources/";
    private static final String TEXT_TO_WRITE = "fruit,quantity" + System.lineSeparator()
            + "banana,50" + System.lineSeparator()
            + "apple,40";
    private static FileWriter fileWriter;

    @BeforeClass
    public static void beforeClass() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void writeToFile_Ok() {
        fileWriter.writeToFile(TEXT_TO_WRITE, PATH_TO_FILE);
        String actual = readFromTestFile(PATH_TO_FILE);
        assertEquals(TEXT_TO_WRITE, actual);
    }

    @Test
    public void writeToFile_EmptyData_Ok() {
        fileWriter.writeToFile("", PATH_TO_FILE);
        String actual = "";
        assertEquals("", actual);
    }

    @Test(expected = WriteNotPossibleException.class)
    public void writeToFile_WrongPath_NotOk() {
        fileWriter.writeToFile(TEXT_TO_WRITE, WRONG_PATH_TO_FILE);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_PathIsNull_NotOk() {
        fileWriter.writeToFile(TEXT_TO_WRITE, null);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_DataIsNull_NotOk() {
        fileWriter.writeToFile(null, PATH_TO_FILE);
    }

    private String readFromTestFile(String pathToFile) {
        File file = new File(pathToFile);
        try {
            List<String> strings = Files.readAllLines(file.toPath());
            return String.join(System.lineSeparator(), strings);
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file " + pathToFile, e);
        }
    }
}
