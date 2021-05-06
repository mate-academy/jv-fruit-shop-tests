package core.basesyntax.service.implementation;

import core.basesyntax.service.FileWriter;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileWriterImplTest {
    private static FileWriter fileWriter;
    private static final String INVALID_FILE_PATH = "src/test/java/resource/.0";
    private static final String VALID_FILE_PATH = "src/test/java/resources/OutputFileTest.csv";

    @BeforeClass
    public static void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    public void testWriteToFile_withValidFilePath_WithisOk() {
        fileWriter.writeToFile("example", VALID_FILE_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void testWriteToFile_withInvalidFilePath_isNotOk() {
        fileWriter.writeToFile("example", INVALID_FILE_PATH);
    }
}
