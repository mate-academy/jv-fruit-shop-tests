package core.basesyntax.service.file;

import core.basesyntax.service.file.impl.FileWriterImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterTest {
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
        String report = "fruit,quantity"
                + System.lineSeparator() + "banana,150"
                + System.lineSeparator() + "apple,50"
                + System.lineSeparator();
        fileWriter.write(PATH_TO_FILE_OUTPUT, report);
        List<String> expected = List.of("fruit,quantity", "banana,150", "apple,50");
        List<String> actual = Files.readAllLines(Path.of(PATH_TO_FILE_OUTPUT));;
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void fileWriteToIncorrectFilePath_notOk() {
        fileWriter.write(INCORRECT_FILE_PATH, TEXT);
    }
}
