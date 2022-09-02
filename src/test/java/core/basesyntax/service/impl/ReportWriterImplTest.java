package core.basesyntax.service.impl;

import core.basesyntax.service.ReportWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportWriterImplTest {
    private static ReportWriter reportWriter;
    private String report;
    private File file;

    @BeforeClass
    public static void beforeAll() {
        reportWriter = new ReportWriterImpl();
    }

    @Before
    public void setUp() {
        report = "fruit,quantity" + System.lineSeparator()
                + "apple,90" + System.lineSeparator()
                + "peach,78" + System.lineSeparator();
        file = new File("src/test/resources/testReport.csv");
    }

    @Test
    public void service_reportWriterWrite_ok() {
        reportWriter.write(report, file);
        String actual = fromFileToString(file);
        String expected = fromFileToString(new File("src/test/resources/testForWriter.csv"));
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void service_reportWriterWriteNull_notOk() {
        report = null;
        reportWriter.write(report, file);
    }

    public String fromFileToString(File file) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            StringBuilder stringBuilder = new StringBuilder();
            String value = bufferedReader.readLine();
            while (value != null) {
                stringBuilder.append(value).append(System.lineSeparator());
                value = bufferedReader.readLine();
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

}
