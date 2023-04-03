package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import core.basesyntax.services.FileReaderService;
import core.basesyntax.services.FileReaderServiceImpl;
import core.basesyntax.services.exception.FileReaderException;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileReaderServiceTests {
    private static FileReaderService fileReader;
    private static final String File = "src/test/java/core/basesyntax/data.csv";

    @BeforeClass
    public static void setFileReader() {
        fileReader = new FileReaderServiceImpl();
    }

    @Test
    public void readFromEmptyFile_Ok() {
        String emptyFile = "src/test/java/core/basesyntax/empty.csv";
        List<String> lines = fileReader.readFromFile(emptyFile);
        int expectedSize = 0;
        int actualSize = lines.size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void readFromFile_Ok() {
        List<String> actualLines = fileReader.readFromFile(File);
        List<String> expectedLines = List.of("type,fruit,quantity", "b,banana,20", "b,apple,100");
        assertEquals(expectedLines, actualLines);
    }

    @Test
    public void readFromFile_NotOk() {
        List<String> actualLines = fileReader.readFromFile(File);
        List<String> expectedLines = List.of("type,fruit,quantity", "p,banana,70", "b,apple,100");
        assertNotEquals(expectedLines, actualLines);
    }

    @Test
    public void readFromNonExistFile() {
        try {
            List<String> actualLines = fileReader.readFromFile("111.csv");
            fail("Expected fileReaderException");
        } catch (FileReaderException ex) {
            assertEquals("Can't read file 111.csv", ex.getMessage());
        }
    }

}
