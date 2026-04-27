import core.basesyntax.service.Reader;
import core.basesyntax.service.impl.CsvReaderImpl;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CsvReaderTest {
    private Reader reader = new CsvReaderImpl();
    
    @Test
    public void readFile_readsFileCorrectly_existingFile() {
        List<String> readLines = reader.readFile("TestFileInput.csv");

        Assertions.assertEquals(3, readLines.size());
        Assertions.assertEquals("type,fruit,quantity", readLines.get(0));
        Assertions.assertEquals("b,banana,20", readLines.get(1));
        Assertions.assertEquals("b,apple,40", readLines.get(2));
    }
    
    @Test
    public void readFile_throwsException_notExistingFile() {
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> reader.readFile("RandomFile.csv"));

        Assertions.assertTrue(exception.getMessage().contains("Can't read file"));
    }
}
