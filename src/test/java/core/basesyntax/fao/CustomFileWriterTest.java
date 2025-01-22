package core.basesyntax.fao;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CustomFileWriterTest {
    private static final String FILE_TO_WRITE = "test.csv";
    private static CustomFileWriter customFileWriter;

    @BeforeAll
    static void beforeAll() {
        customFileWriter = new FileWriterImpl();
    }

    @Test
    void writerOk() {
        customFileWriter.write(FILE_TO_WRITE, "something");
        assertNotNull(customFileWriter);
    }
}
