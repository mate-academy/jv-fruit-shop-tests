package core.basesyntax.service.impl;

import core.basesyntax.service.Writer;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterImplTest {
    private static final String PATH_TO_OUTPUT_FILE = "src/test/resources/output.csv";
    private static final String PATH_TO_NOT_EXISTED_FILE = "src/invalid/resources/outputFile.csv";
    private static Writer writer;
    private static String report;

    @BeforeClass
    public static void beforeClass() {
        writer = new WriterImpl();
        report = "fruit,quantity"
                + System.lineSeparator()
                + "banana,90"
                + System.lineSeparator()
                + "apple,120";
    }

    @Test(expected = RuntimeException.class)
    public void writeFileWithInvalidPath_notOk() {
        writer.writeToFile(report, PATH_TO_NOT_EXISTED_FILE);
    }

    @Test
    public void writeToFile_Ok() {
        writer.writeToFile(report, PATH_TO_OUTPUT_FILE);
    }
}
