package core.basesyntax.operationswithfile;

import java.util.List;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String FALSE_PATH = "src/test/java/core/basesyntax/resources/file2.csv";
    private static final String TRUE_PATH = "src/main/java/resources/operations.csv";
    private static final FileReader fileReader = new FileReaderImpl();

    @Test(expected = RuntimeException.class)
    public void testReadFromFileWithFalsePath_assertException() {
        List<Transaction> operations = fileReader.getOperations(FALSE_PATH);
    }

    @Test
    public void testReadFromFileWithTruePath_ok() {
        List<Transaction> operations = fileReader.getOperations(TRUE_PATH);
    }
}
