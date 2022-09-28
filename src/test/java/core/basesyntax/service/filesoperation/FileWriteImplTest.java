package core.basesyntax.service.filesoperation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;

public class FileWriteImplTest {
    private static final String FILE_PATH = "src/test/resources/report_file";
    private static final String DATA = "fruit,quantity"
                + System.lineSeparator() + "banana,152"
                + System.lineSeparator() + "apple,90";
    private FileWrite fileWrite;

    @Before
    public void setUp() {
        fileWrite = new FileWriteImpl();
    }

    public List<String> read(String filePath) {
        List<String> frits;
        try {
            frits = Files.readAllLines(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Can't find file by path: " + filePath);
        }
        return frits;
    }

    @Test
    public void writeFilePath_Ok() {
        fileWrite.write(FILE_PATH, DATA);
        assertTrue(new File(FILE_PATH).exists());
        assertTrue(new File(FILE_PATH).canRead());
        List<String> readFile = read(FILE_PATH);
        String result = readFile.stream()
                .collect(Collectors.joining(System.lineSeparator()));
        assertEquals(result,DATA);
    }

    @Test(expected = RuntimeException.class)
    public void writeFilePath_NotOk() {
        fileWrite.write("", DATA);
    }

    @Test(expected = RuntimeException.class)
    public void writeFilePathNull_NotOk() {
        fileWrite.write(null, DATA);
    }
}
