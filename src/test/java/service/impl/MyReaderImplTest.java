package service.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import service.MyReader;

public class MyReaderImplTest {
    private static MyReader myReader;

    @BeforeClass
    public static void beforeClass() {
        myReader = new MyReaderImpl();
    }

    @Test
    public void read_validData_ok() {
        List<String> actual = myReader.readFromFile("src/test/resources/valid_file.csv");
        List<String> expected = List.of("test", "test 123", "test Hello!!");
        assertEquals(expected, actual);
    }

    @Test
    public void read_fileWithOneLine_ok() {
        List<String> actual = myReader.readFromFile("src/test/resources/oneLine_file.csv");
        List<String> expected = List.of("Only one line");
        assertEquals(expected, actual);
    }

    @Test
    public void read_EmptyFile_ok() {
        List<String> actual = myReader.readFromFile("src/test/resources/empty_file.csv");
        List<String> expected = List.of();
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void read_nonExistentFile_notOk() {
        myReader.readFromFile("src/test/resources/non-existent.csv");
    }

    @Test(expected = RuntimeException.class)
    public void read_nullPath_notOk() {
        myReader.readFromFile(null);
    }

    @Test(expected = RuntimeException.class)
    public void read_emptyPath_notOk() {
        myReader.readFromFile("");
    }
}
