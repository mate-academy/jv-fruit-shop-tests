package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.FileReaderImpl;
import core.basesyntax.service.impl.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterTest {
    private static final String WRITER_FILE_PATH = "src/test/resources"
            + "/fileWriterResources/output_OK.csv";
    private static final String NOT_EXISTING_FILE_PATH = "";
    private static FileWriter fileWriter;
    private static FileReader fileReader;

    @BeforeClass
    public static void beforeClass() {
        fileReader = new FileReaderImpl();
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void test_writingToFile_OK() {
        String report = "this is text, that should" + System.lineSeparator()
                + "be written to the" + System.lineSeparator()
                + "FILE";
        fileWriter.writeToFile(report, WRITER_FILE_PATH);
        List<String> expected;
        try {
            expected = Files.readAllLines(Path.of(WRITER_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Error while reading file " + WRITER_FILE_PATH, e);
        }
        List<String> actual = fileReader.readFromFile(WRITER_FILE_PATH);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void test_writingToFile_Not_OK() {
        String report = "this is text, that should" + System.lineSeparator()
                + "be written to the" + System.lineSeparator()
                + "FILE";
        fileWriter.writeToFile(report, NOT_EXISTING_FILE_PATH);
    }

}
