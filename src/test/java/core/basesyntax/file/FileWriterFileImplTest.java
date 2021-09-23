package core.basesyntax.file;

import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FileWriterFileImplTest {
    private static final String CORRECT_PATH = "src/test/resources/report_test.csv";
    private static final String INCORRECT_PATH = "";
    private static FileWriter fileWriter;
    private static List<String> content = Collections.singletonList("b,banana,100500");

    @Before
    public void setUp() {
        fileWriter = new FileWriterFileImpl();
    }

    @Test(expected = RuntimeException.class)
    public void write_ToIncorrectFilePath_Ok() {
        fileWriter.write(content, INCORRECT_PATH);
    }

    @Test
    public void write_toCorrectFilePath_Ok() {
        fileWriter.write(content, CORRECT_PATH);
    }
}
