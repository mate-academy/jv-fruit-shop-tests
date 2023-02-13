package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Test;

public class FileReaderImplTest {
    private final FileWriter fileWriter = new FileWriterImpl();
    private final FileReader fileReader = new FileReaderImpl();
    private String filepath;

    @Test
    public void brockenFilePath_NotOk() {
        filepath = "asdf";
        try {
            fileReader.readFromFile(filepath);
        } catch (RuntimeException e) {
            return;
        }
        fail("Runtime exception should be thrown in case of broken filepath");
    }

    @Test
    public void emptyFilePath_NotOk() {
        filepath = "";
        try {
            fileReader.readFromFile(filepath);
        } catch (RuntimeException e) {
            return;
        }
        fail("Runtime exception should be thrown in case of broken filepath");
    }

    @Test
    public void nullPath_NotOk() {
        filepath = null;
        try {
            fileReader.readFromFile(filepath);
        } catch (RuntimeException e) {
            return;
        }
        fail("Runtime exception should be thrown in case of filepath == null");
    }

    @Test
    public void validPathWriteToFile_Ok() {
        filepath = "src/main/resources/TestFile.csv";
        fileWriter.writeToFile("one, two, three, four", filepath);
        List<String> actual = fileReader.readFromFile(filepath);
        assertEquals(1, actual.size());
    }

    @Test
    public void invalidFilePath_NotOk() {
        filepath = "абв/іі/123";
        try {
            fileReader.readFromFile(filepath);
        } catch (RuntimeException e) {
            return;
        }
        fail("Runtime exception should be thrown in case of invalid filepath");
    }

    @AfterClass
    public static void afterClass() throws IOException {
        String filepath = "src/main/resources/TestFile.csv";
        Files.delete(Path.of(filepath));
    }
}
