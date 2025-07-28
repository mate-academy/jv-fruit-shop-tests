package core.basesyntax.service;

import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileWriterImplTest {
    private static final String FILE_TO = "src/test/resources/TestDataBase.csv";
    private Writer writer;

    @BeforeEach
    void beforeEachMethod() {
        writer = new FileWriterImpl();
    }

    @Test
    void checkFileWriter_Ok() {
        String report = "banana,100";
        writer.write(report, FILE_TO);
    }

    @Test
    void checkFileWriter_NotExitsFile_NotOk() {
        String report = "banana,100";
        assertThrows(RuntimeException.class, () ->
                writer.write(report, ""));
    }

    @Test
    void checkFileWriter_NullReport_NotOk() {
        assertThrows(RuntimeException.class, () ->
                writer.write(null, FILE_TO));
    }
}
