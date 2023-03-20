package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriterService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String VALID_PATH = "src/test/resources/testWriter.csv";
    private static final String EXPECTED_CONTENT = "fruit,quantity" + "\r\n" + "apple,80";
    private static WriterService writer;

    @BeforeClass
    public static void beforeClass() throws Exception {
        writer = new WriterServiceImpl();
    }

    @Test
    public void writeDataToFile_ValidWritingCase_Ok() throws IOException {
        File file = new File(VALID_PATH);
        String dataToWrite = "fruit,quantity" + "\r\n" + "apple,80";
        writer.writeDataToFile(dataToWrite, VALID_PATH);
        String expected = EXPECTED_CONTENT;
        String actual = Files.readString(file.toPath());
        assertEquals(expected, actual);
    }
}
