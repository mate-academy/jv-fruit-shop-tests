package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.FileWriterImpl;
import org.junit.jupiter.api.Test;

public class FileWriterImplTest {
    private static final String DATA = """
            type,fruit,quantity
            BALANCE,apple,100
            """;
    private static final String VALID_PATH = "src/test/resources/outputTest.csv";
    private static final String INVALID_PATH = "invalid/path/output.csv";
    private final FileWriterImpl fileWriter = new FileWriterImpl();

    @Test
    void write_validData_ok() throws Exception {
        fileWriter.write(DATA, VALID_PATH);
    }

    @Test
    void write_invalidPath_throwsException() {
        assertThrows(RuntimeException.class, () -> fileWriter
                .write(DATA, INVALID_PATH));
    }
}
