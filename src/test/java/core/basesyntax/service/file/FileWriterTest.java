package core.basesyntax.service.file;

import core.basesyntax.service.file.impl.FileWriterImpl;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterTest {
    private static final String PATH_TO_FILE_INPUT = "src/main/resources/data.csv";
    private static final String PATH_TO_FILE_OUTPUT = "src/main/resources/output.csv";
    private static final String PATH_TO_EXISTING_FILE_OUTPUT = "src/main/resources/existing.csv";
    private static final String INCORRECT_FILE_PATH = "this?path/is/src/incorrect/main/file.csv";
    private static final String TEXT = "Hello, World!";
    private static FileWriter fileWriter;

    @BeforeClass
    public static void beforeClass() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void fileExist_isOk() throws IOException {
        fileWriter.write(PATH_TO_EXISTING_FILE_OUTPUT, TEXT);
        boolean exists = Files.exists(Path.of(PATH_TO_EXISTING_FILE_OUTPUT));
        Assert.assertTrue(exists);
        Files.delete(Path.of(PATH_TO_EXISTING_FILE_OUTPUT));
    }

    @Test
    public void fileWriteData_isOk() throws IOException {
        List<String> recordsFromInputFile =
                Files.readAllLines(Path.of(PATH_TO_FILE_INPUT));
        BufferedWriter writer =
                new BufferedWriter(new java.io.FileWriter(PATH_TO_FILE_OUTPUT));
        for (String record : recordsFromInputFile) {
            writer.write(record + System.lineSeparator());
        }
        writer.close();
        List<String> recordsFromOutputFile =
                Files.readAllLines(Path.of(PATH_TO_FILE_OUTPUT));
        Assert.assertEquals(recordsFromInputFile, recordsFromOutputFile);
    }

    @Test(expected = RuntimeException.class)
    public void fileWriteToIncorrectFilePath_notOk() {
        fileWriter.write(INCORRECT_FILE_PATH, TEXT);
    }
}
