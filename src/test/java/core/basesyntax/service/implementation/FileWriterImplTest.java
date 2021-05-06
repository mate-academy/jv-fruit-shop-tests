package core.basesyntax.service.implementation;

import core.basesyntax.service.FileWriter;
import org.junit.Test;

public class FileWriterImplTest {
    private static final FileWriter FILE_WRITER = new FileWriterImpl();

    @Test
    public void testWriteToFile_withValidFilePath_WithisOk() {
        FILE_WRITER.writeToFile("example", "src/test/java/resources/OutputFileTest.csv");
    }

    @Test(expected = RuntimeException.class)
    public void testWriteToFile_withInvalidFilePath_isNotOk() {
        FILE_WRITER.writeToFile("example", "src/test/java/resource/.0");
    }
}
