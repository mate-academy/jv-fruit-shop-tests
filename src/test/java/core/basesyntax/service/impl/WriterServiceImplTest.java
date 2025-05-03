package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static WriterService writerService;
    private static final String TEST_OUTPUT = "src/test/resources/test_output.csv";
    private static final String RESOURCES_TO_WRITE = "fruit,quantity\nbanana,100\napple,5\n";

    @BeforeClass
    public static void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void write_in_NullableFile_NotOk() {
        writerService.writeToFile(null, RESOURCES_TO_WRITE);
    }

    @Test
    public void write_CorrectData_Ok() {
        writerService.writeToFile(TEST_OUTPUT, RESOURCES_TO_WRITE);
    }

    @Test(expected = NullPointerException.class)
    public void write_Nullable_NotOk() {
        writerService.writeToFile(TEST_OUTPUT, null);
    }
}
