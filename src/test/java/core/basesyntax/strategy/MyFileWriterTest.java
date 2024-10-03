package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

public class MyFileWriterTest {
    private static final List<String> EXPECTED_LIST = List.of("fruit,quantity",
            "banana,152",
            "apple,90");
    private static final String INVALID_PATH = null;
    private static final String FILE_PATH = "src/test/resources/test_report.csv";
    private static final String REPORT_STRING = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "apple,90";
    private static final MyFileWriter myFileWriter = new MyFileWriterImpl();
    private static final MyFileReader myFileReader = new MyFileReaderImpl();

    @Test
    public void myFileWriter_ValidData_Ok() {
        myFileWriter.write(REPORT_STRING,FILE_PATH);
        List<String> actulList = myFileReader.read(FILE_PATH);
        assertEquals(actulList, EXPECTED_LIST);
    }

    @Test
    public void myFileWriter_InvalidPath_NotOk() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            myFileWriter.write(REPORT_STRING, INVALID_PATH);
        });
    }

}
