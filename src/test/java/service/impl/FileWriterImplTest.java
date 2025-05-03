package service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.FileWriter;

class FileWriterImplTest {
    private static final String OUTPUT_TEST_FILE = "src/test/java/service/impl/toTestFile.csv";
    private static FileWriter fileWriter;
    private final String testStringForWrite = """
            fruit,quantity
            banana,145
            apple,90""";

    @BeforeAll
    static void beforeAll() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void write_nullReport_notOk() {
        assertThrows(RuntimeException.class, () -> fileWriter.write(null, OUTPUT_TEST_FILE));
    }

    @Test
    void write_nullFileName_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.write(testStringForWrite, null));
    }

    @Test
    void write_Ok() {
        assertTrue(fileWriter.write(testStringForWrite, OUTPUT_TEST_FILE));
    }
}
