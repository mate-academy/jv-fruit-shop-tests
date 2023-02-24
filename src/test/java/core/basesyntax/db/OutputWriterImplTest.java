package core.basesyntax.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.exception.OutputWriteException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

public class OutputWriterImplTest {
    private static final String DEFAULT_FILE_PATH = "src/main/resources/default.txt";
    private static final String DEFAULT_FILE_CONTENT = "Lorem ipsum dolor sit amet, "
            + "consectetur adipiscing elit, sed"
            + " do eiusmod tempor incididunt ut labore et dolore magna aliqua. "
            + "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris"
            + " nisi ut aliquip ex ea commodo consequat.";
    private OutputWriter outputWriter;

    @Before
    public void setUp() {
        outputWriter = new OutputWriterImpl();
    }

    @Test
    public void generateReport_twoArgumentsNull_notOk() {
        try {
            outputWriter.writeToCsv(null, null);
        } catch (OutputWriteException e) {
            return;
        }
        fail("If arguments are null then generateReport should throw OutputWriteException");
    }

    @Test
    public void generateReport_firstArgumentIsNull_notOk() {
        try {
            outputWriter.writeToCsv(null, DEFAULT_FILE_PATH);
        } catch (OutputWriteException e) {
            return;
        }
        fail("If argument is null then generateReport should throw OutputWriteException");
    }

    @Test
    public void generateReport_secondArgumentIsNull_notOk() {
        try {
            outputWriter.writeToCsv(DEFAULT_FILE_CONTENT, null);
        } catch (OutputWriteException e) {
            return;
        }
        fail("If argument is null then generateReport should throw OutputWriteException");
    }

    @Test
    public void generateReport_writeToFile_ok() {
        outputWriter.writeToCsv(DEFAULT_FILE_CONTENT, DEFAULT_FILE_PATH);

        StringBuilder actual = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(DEFAULT_FILE_PATH))) {
            String value = br.readLine();
            while (value != null) {
                actual.append(value).append(System.lineSeparator());
                value = br.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals("generateReport should not change data while writing",
                DEFAULT_FILE_CONTENT, actual.toString().trim());
    }
}
