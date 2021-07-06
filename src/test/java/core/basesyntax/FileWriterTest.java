package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileWriter;
import core.basesyntax.service.impl.FileReaderImpl;
import core.basesyntax.service.impl.FileWriterImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class FileWriterTest {
    private static final String WRITER_FILE_PATH = "src/test/resources"
            + "/fileWriterResources/output_OK.csv";
    private static final String NOT_EXISTING_FILE_PATH = "";
    private final FileWriter fileWriter = new FileWriterImpl();
    private final FileReader fileReader = new FileReaderImpl();

    @Test
    public void test_writingToFile_OK() {
        String report = "this is text, that should" + System.lineSeparator()
                + "be written to the" + System.lineSeparator()
                + "FILE";
        fileWriter.writeToFile(report, WRITER_FILE_PATH);
        List<String> expected = new ArrayList<>();
        expected.add("this is text, that should");
        expected.add("be written to the");
        expected.add("FILE");
        List<String> actual = fileReader.readFromFile(WRITER_FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test
    public void test_writingToFile_NotT_OK() {
        String report = "this is text, that should" + System.lineSeparator()
                + "be written to the" + System.lineSeparator()
                + "FILE";
        assertThrows(RuntimeException.class,
                () -> fileWriter.writeToFile(report, NOT_EXISTING_FILE_PATH));
    }

}
