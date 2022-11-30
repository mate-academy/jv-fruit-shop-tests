package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.services.FileCreatorImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.Test;

public class FileCreatorTest {
    private static final String FRUIT_REPORT_FILE_NAME = "src/test/resources/report.csv";
    private static final String EMPTY_FILE_NAME = "";
    private final FileCreatorImpl fileCreator = new FileCreatorImpl();

    @Test
    public void createFile_Ok() {
        String eol = System.getProperty("line.separator");
        StringBuilder expected = new StringBuilder();
        expected.append("fruit")
                .append(',')
                .append("quantity")
                .append(eol);
        expected.append("banana")
                .append(',')
                .append("100")
                .append(eol);
        expected.append("apple")
                .append(',')
                .append("10")
                .append(eol);

        fileCreator.createFile(expected.toString(), FRUIT_REPORT_FILE_NAME);
        File file = new File(FRUIT_REPORT_FILE_NAME);
        String actual;
        try {
            actual = Files.readString(file.toPath());
            assertEquals(expected.toString(), actual);
        } catch (IOException e) {
            fail("Unexpected exception was thrown for transactions file "
                    + FRUIT_REPORT_FILE_NAME + " is not founded");
        }
    }

    @Test(expected = RuntimeException.class)
    public void createFile_EmptyPath_NotOk() {
        fileCreator.createFile("test", EMPTY_FILE_NAME);
    }
}
