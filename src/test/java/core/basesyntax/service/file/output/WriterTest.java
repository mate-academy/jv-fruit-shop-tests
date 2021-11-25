package core.basesyntax.service.file.output;

import core.basesyntax.exception.MyCustomIOException;
import core.basesyntax.service.file.input.Reader;
import core.basesyntax.service.file.input.ReaderImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterTest {
    private static Writer writer;
    private static Reader reader;
    private static final String VALID_OUTPUT_DATA_PATH = "src/test/resources/outputData.csv";
    private static final StringBuilder stringBuilder = new StringBuilder();

    @BeforeClass
    public static void beforeClass() {
        stringBuilder.append("fruit,quantity").append(System.lineSeparator())
                .append("banana,100").append(System.lineSeparator())
                .append("apple,100").append(System.lineSeparator());
        writer = new WriterImpl();
        reader = new ReaderImpl();
    }

    @Test
    public void writeToFile_validDataAndPath_ok() {
        StringBuilder sb = new StringBuilder();
        String actual = stringBuilder.toString();
        writer.write(actual, VALID_OUTPUT_DATA_PATH);
        for (String s : reader.read(VALID_OUTPUT_DATA_PATH)) {
            sb.append(s).append(System.lineSeparator());
        }
        String expected = sb.toString();
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void writeToFile_nullPath_notOk() {
        String expected = stringBuilder.toString();
        writer.write(expected, null);
    }

    @Test(expected = NullPointerException.class)
    public void writeToFile_nullData_notOk() {
        writer.write(null, VALID_OUTPUT_DATA_PATH);
    }

    @Test(expected = MyCustomIOException.class)
    public void writeToFile_emptyFilePath_notOk() {
        String expected = stringBuilder.toString();
        writer.write(expected, "");
    }

}
