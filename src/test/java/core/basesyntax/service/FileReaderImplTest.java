package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class FileReaderImplTest {
    private FileWriter fileWriter;
    private FileReader fileReader;

    @Before
    public void setUp() {
        fileWriter = new FileWriterImpl();
        fileReader = new FileReaderImpl();
    }

    @Test
    public void readFromFile_InvalidFilePath_NotOk() {
        String filepath = "asdf";
        try {
            fileReader.readFromFile(filepath);
        } catch (RuntimeException e) {
            return;
        }
        fail("Runtime exception should be thrown in case of broken filepath");
    }

    @Test
    public void readFromFile_EmptyFilePath_NotOk() {
        String filepath = "";
        try {
            fileReader.readFromFile(filepath);
        } catch (RuntimeException e) {
            return;
        }
        fail("Runtime exception should be thrown in case of broken filepath");
    }

    @Test
    public void readFromFile_NullFilePath_NotOk() {
        String filepath = null;
        try {
            fileReader.readFromFile(filepath);
        } catch (RuntimeException e) {
            return;
        }
        fail("Runtime exception should be thrown in case of filepath == null");
    }

    @Test
    public void readFromFile_ValidFilePathWriteToFile_Ok() {
        String filepath = "src/main/resources/TestFile.csv";
        fileWriter.writeToFile("one, two, three, four", filepath);
        List<String> actual = fileReader.readFromFile(filepath);
        assertEquals(1, actual.size());
    }

    @AfterClass
    public static void afterClass() throws IOException {
        String filepath = "src/main/resources/TestFile.csv";
        Files.delete(Path.of(filepath));
    }
}
