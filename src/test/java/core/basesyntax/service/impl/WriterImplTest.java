package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.Writer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterImplTest {
    private static final String WORKER_FILE = "src/main/resources/testOutput.csv";
    private static final String UNREAL_FILE = "src/main/resources/testUnreal.csv";
    private static final String DATA_RESULT = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator()
            + "apple,90" + System.lineSeparator();
    private static Writer writer;

    @BeforeClass
    public static void beforeClass() {
        writer = new WriterImpl();
    }

    @Test
    public void writer_correctData_isOk() {
        writer.writeReport(WORKER_FILE, DATA_RESULT);
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(WORKER_FILE))) {
            String text = bufferedReader.readLine();
            while (text != null) {
                stringBuilder.append(text).append(System.lineSeparator());
                text = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(stringBuilder.toString(), DATA_RESULT);
    }

    @Test
    public void write_toUnrealFile_isOk() {
        writer.writeReport(UNREAL_FILE, DATA_RESULT);
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(UNREAL_FILE))) {
            String text = bufferedReader.readLine();
            while (text != null) {
                stringBuilder.append(text).append(System.lineSeparator());
                text = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(stringBuilder.toString(), DATA_RESULT);
    }

    @Test (expected = RuntimeException.class)
    public void write_toNull_isNotOk() {
        writer.writeReport(null, DATA_RESULT);
    }
}
