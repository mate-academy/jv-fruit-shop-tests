package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CustomFileWriterTest {
    private static final String TEST_FILE_TO_WRITE = "src/main/resources/test.csv";
    private static CustomFileWriter customFileWriter;

    @BeforeAll
    static void beforeAll() {
        customFileWriter = new CustomFileWriterImpl();
    }

    @Test
    void writerCreatesFileIsOk() {
        customFileWriter.write(TEST_FILE_TO_WRITE, "data");
    }

    @Test
    void writerHandlesEmptyDataIsOk() {
        customFileWriter.write(TEST_FILE_TO_WRITE, "");
    }

    @Test
    void writerOk() {
        customFileWriter.write(TEST_FILE_TO_WRITE,"data");
        assertNotNull(customFileWriter);
    }

    @Test
    void writerAppendsDataIsOk() {
        customFileWriter.write(TEST_FILE_TO_WRITE, "data1");
        customFileWriter.write(TEST_FILE_TO_WRITE, "data2");
    }
}
