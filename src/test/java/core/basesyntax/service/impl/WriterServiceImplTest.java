package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.ReportMakerService;
import core.basesyntax.service.WriterService;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class WriterServiceImplTest {
    private static final String VALID_PATH = "src/test/resources/testWriter.csv";
    private static final String FRUIT = "apple";
    private static final int QUANTITY = 80;
    private static final String EXPECTED_CONTENT = "fruit,quantity" + System.lineSeparator() + "apple,80";
    private static ReportMakerService reportMaker;
    private static WriterService writer;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        reportMaker = new ReportMakerServiceImpl();
        writer = new WriterServiceImpl();
    }

    @Before
    public void setUp() throws Exception {
        FruitStorage.fruitStorage.put(FRUIT, QUANTITY);
    }

    @After
    public void tearDown() throws Exception {
        FruitStorage.fruitStorage.clear();
    }

    @Test
    public void writeDataToFile_ValidWritingCase_Ok() throws IOException {
        File file = new File(VALID_PATH);
        String dataToWrite = reportMaker.generateReport(FruitStorage.fruitStorage);
        String expected = EXPECTED_CONTENT;
        writer.writeDataToFile(dataToWrite, VALID_PATH);
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
