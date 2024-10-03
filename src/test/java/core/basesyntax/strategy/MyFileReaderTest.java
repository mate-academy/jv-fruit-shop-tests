package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

public class MyFileReaderTest {
    private static final String INVALID_PATH = null;
    private static final String FILE_PATH = "src/test/resources/test_report.csv";
    private static final MyFileReader myFileReader = new MyFileReaderImpl();

    @Test
    public void myFileReader_ValidData_Ok() {
        List<String> list = myFileReader.read(FILE_PATH);
        assertEquals(list, List.of("fruit,quantity",
                "banana,152",
                "apple,90"));
    }

    @Test
    public void myFileReader_InvalidPath_NotOk() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            myFileReader.read(INVALID_PATH);
        });
    }
}
