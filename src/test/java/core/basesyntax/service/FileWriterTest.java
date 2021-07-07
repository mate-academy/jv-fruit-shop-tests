package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

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

    @BeforeClass
    public static void beforeClass() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void writeToFile_validString_OK() {
        String report = "this is text, that should" + System.lineSeparator()
                + "be written to the" + System.lineSeparator()
                + "FILE";
        fileWriter.writeToFile(report, WRITER_FILE_PATH);
        List<String> actual;
        try {
            actual = Files.readAllLines(Path.of(WRITER_FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Error while reading file " + WRITER_FILE_PATH, e);
        }
        List<String> expected = List.of("this is text, that should", "be written to the", "FILE");
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_notExistingFile_Not_OK() {
        String report = "this is text, that should" + System.lineSeparator()
                + "be written to the" + System.lineSeparator()
                + "FILE";
        fileWriter.writeToFile(report, NOT_EXISTING_FILE_PATH);
    }
}
