package core.basesyntax.filework;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterTest {
    private static final String CORRECT_PATH_FOR_WRITE = "src/test/resources/test_write_file.csv";
    private static final String CONTENT = "content";
    private static FileWriter writer;

    @BeforeClass
    public static void beforeClass() {
        writer = new CsvFileWriterImpl();
    }

    @Test
    public void fileWriterWriteToFile_Ok() {
        writer.write(CORRECT_PATH_FOR_WRITE, CONTENT);
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new java.io.FileReader(CORRECT_PATH_FOR_WRITE));
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }
        String line;
        try {
            line = bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException();
        }
        assertEquals(CONTENT, line);
    }
}
