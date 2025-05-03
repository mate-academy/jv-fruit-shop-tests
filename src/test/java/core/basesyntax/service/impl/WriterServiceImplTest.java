package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.WriterService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class WriterServiceImplTest {
    private static final String VALID_PATH = "src/test/resources/testWriter.csv";
    private static final String DATA_TO_WRITE = "fruit,quantity"
            + System.lineSeparator() + "apple,90";
    private static final String EXPECTED_CONTENT = "fruit,quantity"
            + System.lineSeparator() + "apple,90";
    private static WriterService writer;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        writer = new WriterServiceImpl();
    }

    @After
    public void tearDown() {
        FruitStorage.fruitStorage.clear();
    }

    @Test
    public void writeData_zeroLengthStringInput_notOk() {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Can't write zero string");
        String dataToWrite = "";
        writer.writeData(dataToWrite, VALID_PATH);
    }

    @Test
    public void writeData_validWritingCase_ok() throws IOException {
        File file = new File(VALID_PATH);
        String expected = EXPECTED_CONTENT;
        writer.writeData(DATA_TO_WRITE, VALID_PATH);
        String actual = Files.readString(file.toPath());
        assertEquals(expected, actual);
    }
}
