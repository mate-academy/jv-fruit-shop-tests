package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.repository.FruitDB;
import core.basesyntax.service.WriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class WriterServiceImplTest {
    private static final String EMPTY_STRING = "";
    private static final String VALID_FILE = "src/test/resources/test_writer2.csv";
    private static final String INVALID_FILE = "src/test/test/resources/test_writer.csv";
    private static final String RESULT_REGULAR_CASE = "fruit,quantity\nbanana,20";
    private static final String FRUIT = "banana";
    private static final int AMOUNT = 20;
    private static WriterService writerService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        writerService = new WriterServiceImpl();
        FruitDB.fruitsOnStock.put(FRUIT, AMOUNT);
    }

    @After
    public void tearDown() {
        FruitDB.fruitsOnStock.clear();
    }

    @Test
    public void write_emptyStringAsArgument_notOk() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Can't write to file");
        writerService.write(EMPTY_STRING);
    }

    @Test
    public void write_nullAsArgument_notOk() {
        thrown.expect(NullPointerException.class);
        writerService.write(null);
    }

    @Test
    public void write_regularWritingCase_Ok() throws IOException {
        FruitDB.fruitsOnStock.clear();
        FruitDB.fruitsOnStock.put(FRUIT, AMOUNT);
        writerService.write(VALID_FILE);
        String content = Files.readString(Paths.get(VALID_FILE));
        assertEquals(RESULT_REGULAR_CASE, content);
    }

    @Test
    public void write_invalidFile_notOk() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Can't write to file");
        writerService.write(INVALID_FILE);
    }
}
