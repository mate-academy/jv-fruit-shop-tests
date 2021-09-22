package core.basesyntax.service.writer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

public class ReportWriterImplTest {
    private static ReportWriter reportWriter;
    private static String pathFile;
    private static String toReportString;

    @Before
    public void setUp() {
        reportWriter = new ReportWriterImpl();
        toReportString = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90";
        pathFile = "src/test/resources/reportFile.csv";
    }

    @Test
    public void reportWriterImplTest_Ok() {
        reportWriter.write(pathFile, toReportString);
        StringBuilder readString = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(pathFile));
            String tmp;
            while (null != (tmp = reader.readLine())) {
                readString.append(tmp).append(System.lineSeparator());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(readString.toString().trim(), toReportString);
    }

    @Test
    public void reportWriterImplTest_notOk() {
        reportWriter.write(pathFile, toReportString);
        StringBuilder readString = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(pathFile));
            String tmp;
            while (null != (tmp = reader.readLine())) {
                readString.append(tmp).append(System.lineSeparator());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotEquals(readString.toString().trim(),
                (toReportString + System.lineSeparator() + "zzz"));
    }
}
