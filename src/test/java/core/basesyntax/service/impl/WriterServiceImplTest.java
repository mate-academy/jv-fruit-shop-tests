package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.WriterService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class WriterServiceImplTest {
    private static final String VALID_PATH = "src/test/resources/testWriter.csv";
    private static final String DATA_TO_WRITE = "fruit,quantity\napple,80";
    private static final String EXPECTED_CONTENT = "fruit,quantity\napple,80";
    private static WriterService writer;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        writer = new WriterServiceImpl();
    }

    @Test
    public void writeDataToFile_ValidWritingCase_Ok() throws IOException {
        File file = new File(VALID_PATH);
        writer.writeDataToFile(DATA_TO_WRITE, VALID_PATH);
        String expected = EXPECTED_CONTENT;
        String actual = Files.readString(file.toPath());
        assertEquals(expected, actual);
    }

    @Test
    public void writeDataToFile_ZeroLengthStringInput_NotOk() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Can't write zero string");
        String dataToWrite = "";
        writer.writeDataToFile(dataToWrite, VALID_PATH);
    }
}
