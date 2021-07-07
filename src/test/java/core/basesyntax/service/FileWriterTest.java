package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dbtest.StorageTest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Before;
import org.junit.Test;

public class FileWriterTest {
    private static final String PATH_FILE_REPORT =
            "src/test/resources/csv_file_writer_test/storage.csv";
    private static final String REPORT_IS_NOT_VALID = "fruit_quantity" + System.lineSeparator()
            + "apple90\n"
            + "banana152";
    private static final String REPORT_VALID = "fruit,quantity" + System.lineSeparator()
            + "apple,90" + System.lineSeparator()
            + "banana,152";
    private FileWriter fileWriter;

    @Before
    public void setFileWriter() {
        fileWriter = new FileWriter();
        StorageTest.storage.clear();
    }

    @Test
    public void fileWriter_successfulWrite_ok() {
        fileWriter.writeToFile(REPORT_VALID, PATH_FILE_REPORT);
        String actual;
        try {
            actual = String
                    .join(System.lineSeparator(), Files.readAllLines(Path.of(PATH_FILE_REPORT)));
        } catch (IOException e) {
            throw new RuntimeException(
                    "Its not available to read file: " + PATH_FILE_REPORT + " " + REPORT_VALID, e);
        }
        assertEquals(REPORT_VALID, actual);
    }

    @Test(expected = RuntimeException.class)
    public void fileWriter_NullArgs_notOk() {
        fileWriter.writeToFile(null, "");
    }

    @Test(expected = RuntimeException.class)
    public void fileWriter_reportIsNotMatchedRegex() {
        fileWriter.writeToFile(REPORT_IS_NOT_VALID, "");
    }
}
