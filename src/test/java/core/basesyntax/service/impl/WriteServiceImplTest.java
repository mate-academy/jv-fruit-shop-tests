package core.basesyntax.service.impl;

import core.basesyntax.service.WriteService;
import org.junit.Before;
import org.junit.Test;

public class WriteServiceImplTest {
    private static final String OUTPUT_FILE_PATH = "src/test/java/resources/report.csv";
    private static final String FILE_CONTENT = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator() + "apple,90";
    private WriteService writeService;

    @Before
    public void setUp() {
        writeService = new WriteServiceImpl();
    }

    @Test
    public void writeService_ExistedPath_Ok() {
        writeService.writeToFile(FILE_CONTENT, OUTPUT_FILE_PATH);
    }

    @Test(expected = RuntimeException.class)
    public void writeService_EmptyPath_NotOk() {
        writeService.writeToFile(FILE_CONTENT, "");
    }

    @Test (expected = RuntimeException.class)
    public void writeService_nullFile_NotOk() {
        writeService.writeToFile(null, OUTPUT_FILE_PATH);
    }
}
