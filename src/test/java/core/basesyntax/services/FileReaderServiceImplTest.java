package core.basesyntax.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import core.basesyntax.services.exception.FileReaderException;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceImplTest {
    private static FileReaderService fileReader;
    private static final String File = "src/test/java/core/basesyntax/data.csv";

    @BeforeClass
    public static void setFileReader() {
        fileReader = new FileReaderServiceImpl();
    }

    @Test
    public void read_emptyFile_ok() {
        String emptyFile = "src/test/java/core/basesyntax/empty.csv";
        List<String> lines = fileReader.read(emptyFile);
        int expectedSize = 0;
        int actualSize = lines.size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void read_file_ok() {
        List<String> actual = fileReader.read(File);
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20", "b,apple,100");
        assertEquals(expected, actual);

        List<String> unExpected = List.of("type,fruit,quantity", "p,banana,70", "b,apple,100");
        assertNotEquals(unExpected, actual);
    }

    @Test
    public void read_nonExistFile_notOk() {
        try {
            List<String> actualLines = fileReader.read("111.csv");
            fail("Expected fileReaderException");
        } catch (FileReaderException ex) {
            assertEquals("Can't read file 111.csv", ex.getMessage());
        }
    }
}
