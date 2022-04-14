package core.basesyntax.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    public static final String CORRECT_PATH = "src/test/java/resources/output_test.csv";
    public static final String INCORRECT_PATH = "";
    public static final String TEST_TEXT = "File is written OK!";
    public static final String FIRST_LINE_WITH_DESCRIPTION = System.lineSeparator();
    private static FileWriterImpl writerService;
    private static FileReader fileReader;
    private static FileWriter fileWriter;
    private List<String> stringFromOutputTestFile;

    @BeforeClass
    public static void setUp() {
        fileWriter = new FileWriterImpl();
        fileReader = new FileReaderImpl();
    }

    @Test
    public void write_correctPath_ok() {
        File file = new File(CORRECT_PATH);
        file.delete();
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file " + CORRECT_PATH, e);
        }
        List<String> expected = new ArrayList<String>();
        expected.add(TEST_TEXT);
        fileWriter.write(CORRECT_PATH, FIRST_LINE_WITH_DESCRIPTION);
        fileWriter.write(CORRECT_PATH, TEST_TEXT);
        List<String> actual = fileReader.read(CORRECT_PATH);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void write_incorrectPath_notOk() {
        Assert.assertThrows("Exception should be thrown",
                RuntimeException.class, () -> writerService.write(INCORRECT_PATH, ""));
    }
}
