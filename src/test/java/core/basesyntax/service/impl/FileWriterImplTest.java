package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.FileWriter;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static final String VALID_FILE_SOURCE = "src/test/resources/test_file_writer.csv";
    private static final String INVALID_FILE_SOURCE
            = "wrong_path/src/resources/test_file_reader.csv";
    private static final String DATA = "fruit,quantity" + System.lineSeparator()
            + "banana,252" + System.lineSeparator()
            + "apple,90" + System.lineSeparator();
    private static FileWriter fileWriter;

    @BeforeClass
    public static void beforeClass() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void writeFileWithValidFileSource_Ok() {
        boolean expected = true;
        boolean actual = fileWriter.writeFile(VALID_FILE_SOURCE, DATA);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeFileWithInvalidFileSource_NotOk() {
        fileWriter.writeFile(INVALID_FILE_SOURCE, DATA);
    }
}
