package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.ReportMakerService;
import core.basesyntax.service.WriterService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String VALID_PATH = "src/test/resources/testWriter.csv";
    private static final String FRUIT = "apple";
    private static final int QUANTITY = 80;
    private static final String EXPECTED_CONTENT = "fruit,quantity" + "\r\n" + "apple,80" + "\r\n";
    private static WriterService writer;
    private static ReportMakerService reportMaker;

    @BeforeClass
    public static void beforeClass() throws Exception {
        reportMaker = new ReportMakerServiceImpl();
        writer = new WriterServiceImpl();
        FruitStorage.fruitStorage.put(FRUIT, QUANTITY);
    }

    @After
    public void tearDown() throws Exception {
        FruitStorage.fruitStorage.clear();
    }

    @Test
    public void writeDataToFile_ValidWritingCase_Ok() throws IOException {
        File file = new File(VALID_PATH);
        String dataToWrite = "fruit,quantity" + "\r\n" + "apple,80" + "\r\n";
        writer.writeDataToFile(dataToWrite, VALID_PATH);
        String expected = EXPECTED_CONTENT;
        String actual = Files.readString(file.toPath());
        assertEquals(expected, actual);
    }
}
