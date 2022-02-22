package dao;

import static org.junit.Assert.assertEquals;

import dao.impl.FileHandlerImpl;
import org.junit.Test;

public class FileHandlerTest {
    private static final String noFileLink = "";
    private static final String filePathData =
            "src/test/java/resources/data.csv";
    private static final String destFile =
            "src/test/java/resources/storage.csv";
    private static final FileHandler fileHandler = new FileHandlerImpl();
    private static final String source = "type,fruit,quantity";

    @Test
    public void readData_Ok() {
        String readData = fileHandler.readData(filePathData);
        boolean expected = true;
        boolean actual = source.equals(readData);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void readData_noSuchFile_Ok() {
        fileHandler.readData(noFileLink);
    }

    @Test(expected = RuntimeException.class)
    public void write_noSuchFile_Ok() {
        fileHandler.writeData(source, noFileLink);
    }

    @Test
    public void write_Ok() {
        fileHandler.writeData(source, destFile);
    }
}
