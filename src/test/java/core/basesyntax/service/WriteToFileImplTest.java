package core.basesyntax.service;

import core.basesyntax.service.impl.WriteToFileImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriteToFileImplTest {
    private static WriteToFile writer;
    private static final String VALID_REPORT = "banana,50\napple,40";
    private static final String FILE_PATH_WRITE = "src/test/java/core"
            + "/basesyntax/resourses/report.csv";
    private static final String NULL_VALUE = null;

    @BeforeClass
    public static void beforeClass() {
        writer = new WriteToFileImpl();
    }

    @Test(expected = RuntimeException.class)
    public void writeToInvalidPath_notOk() {
        writer.write(VALID_REPORT, "");
    }

    @Test(expected = RuntimeException.class)
    public void writeInvalidData_notOk() {
        writer.write(NULL_VALUE, FILE_PATH_WRITE);
    }

    @Test
    public void writeValidParameters_ok() {
        writer.write(VALID_REPORT, FILE_PATH_WRITE);
    }
}
