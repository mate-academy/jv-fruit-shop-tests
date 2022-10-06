package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileWriterServiceTest {
    private static WriterService writer;

    @BeforeClass
    public static void beforeClass() {
        writer = new CsvFileWriterService();
    }

    @Test
    public void write_Data_To_Correct_File_ok() {
        String testString = "test"
                + System.lineSeparator()
                + "writing"
                + System.lineSeparator()
                + "successful";
        List<String> expected = List.of(
                "test",
                "writing",
                "successful"
        );
        String filePath = "src/test/resources/write-test.csv";
        writer.writeToFile(testString, filePath);
        ReaderService reader = new CsvFileReaderService();
        assertEquals(expected, reader.readFromFile(filePath));
    }

    @Test(expected = RuntimeException.class)
    public void write_Data_To_Incorrect_File_notOk() {
        writer.writeToFile("test", "src/test/resources/write-test1.csv");
    }
}
