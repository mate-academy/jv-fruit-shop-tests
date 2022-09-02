package core.basesyntax.service.impl;

import core.basesyntax.service.WriterService;
import org.junit.Before;
import org.junit.Test;

public class WriterServiceImplTest {
    private WriterService writerService;
    private String testOutput;
    private String resourcesToWrite;

    @Before
    public void setUp() throws Exception {
        writerService = new WriterServiceImpl();
        testOutput = "src/test/resources/test_output.csv";
        resourcesToWrite = "fruit,quantity\nbanana,100\napple,5\n";
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
