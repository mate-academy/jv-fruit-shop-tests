package core.basesyntax.service;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;
import java.util.List;
import org.junit.Test;

public class FileReaderImplTest {
    FileReader fileReader = new FileReaderImpl();
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
        List<String> actual = fileReader.readFromFile(filepath);
        assertEquals(11, actual.size());
    }
}