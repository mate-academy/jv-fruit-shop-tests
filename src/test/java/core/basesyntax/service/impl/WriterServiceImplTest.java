package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static WriterService writerService;
    private static final String testOutput = "src/test/resources/test_output.csv";
    private static final String resourcesToWrite = "fruit,quantity\nbanana,100\napple,5\n";

    @BeforeClass
    public static void setUp() throws Exception {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void write_In_NonExistenFile_Ok() {
        String file = "src/test/resources/nonExistedFile.csv";
        writerService.writeToFile(file, resourcesToWrite);
    }

    @Test(expected = RuntimeException.class)
    public void write_in_NullableFile_NotOk() {
        writerService.writeToFile(null, resourcesToWrite);
    }

    @Test
    public void writeToFile_Ok() {
        writerService.writeToFile(testOutput, resourcesToWrite);
    }

    @Test(expected = NullPointerException.class)
    public void write_Nullable_NotOk() {
        writerService.writeToFile(testOutput, null);
    }
}
