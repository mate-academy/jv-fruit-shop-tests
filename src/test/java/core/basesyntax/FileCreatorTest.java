package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.services.FileCreatorImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.Test;

public class FileCreatorTest {
    private static final String FRUIT_REPORT_FILE_NAME = "src/test/resources/report.csv";
    private static final String EMPTY_FILE_NAME = "";

    @Test
    public void fileCreate_Ok() {
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

        FileCreatorImpl fileCreator = new FileCreatorImpl();
        fileCreator.createFile(expected.toString(), FRUIT_REPORT_FILE_NAME);
        File file = new File(FRUIT_REPORT_FILE_NAME);
        String actual;
        try {
            actual = Files.readString(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException("Transactions file "
                    + FRUIT_REPORT_FILE_NAME + " not found", e);
        }
        assertEquals(expected.toString(), actual);
    }

    @Test(expected = RuntimeException.class)
    public void fileCreator_EmptyPath_NotOk() {
        FileCreatorImpl fileCreator = new FileCreatorImpl();
        fileCreator.createFile("test", EMPTY_FILE_NAME);
    }
}
