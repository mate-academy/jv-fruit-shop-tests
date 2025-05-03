package dao;

import static org.junit.Assert.assertEquals;

import dao.impl.FileHandlerImpl;
import java.io.File;
import java.nio.file.Files;
import javax.imageio.IIOException;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileHandlerTest {
    private static final String noFileLink = "";
    private static final String filePathData =
            "src/test/java/resources/data.csv";
    private static final String destFile =
            "src/test/java/resources/storage.csv";
    private static final FileHandler fileHandler = new FileHandlerImpl();
    private static final String source = "type,fruit,quantity";

    @BeforeClass
    public static void beforeClass() throws Exception {
        try {
            Files.write(new File(filePathData).toPath(), source.getBytes());
        } catch (IIOException e) {
            throw new RuntimeException("Can`t write to file" + filePathData);
        }

    }

    @Test
    public void readData_validData_ok() {
        String readData = fileHandler.readData(filePathData);
        assertEquals(source, readData);
    }

    @Test(expected = RuntimeException.class)
    public void readData_noSuchFile_notOk() {
        fileHandler.readData(noFileLink);
    }

    @Test(expected = RuntimeException.class)
    public void write_noSuchFile_notOk() {
        fileHandler.writeData(source, noFileLink);
    }

    @Test
    public void write_Ok() {
        fileHandler.writeData(source, destFile);
    }
}
