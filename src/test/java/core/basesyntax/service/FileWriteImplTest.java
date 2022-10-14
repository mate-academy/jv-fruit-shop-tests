package core.basesyntax.service;

import core.basesyntax.db.Storage;
import java.io.BufferedReader;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

public class FileWriteImplTest {
    public static final String FILE_NAME = "src/main/java/core/basesyntax/resources/result.csv";
    private ReportCreator reportCreator = new CsvReportCreatorImpl();
    private FileWrite fileWrite = new FileWriteImpl();

    @Test
    public void fileWriteImpl_Ok() {
        StringBuilder stringBuilder = new StringBuilder();
        String expected = "banana,150\napple,150\nananas,150\n";
        String string = reportCreator.createReport(Storage.getStorage());
        fileWrite.writeDataToFile(string, FILE_NAME);

        try (BufferedReader bufferReader =
                     new BufferedReader(
                             new java.io.FileReader(
                                     "src/main/java/core/basesyntax/resources/report.csv"))) {
            String value = bufferReader.readLine();
            while (value != null) {
                stringBuilder.append(value)
                        .append("\n");
                value = bufferReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("File cannot be read");
        }
        String actual = stringBuilder.toString();
        Assert.assertEquals(expected, actual);
    }
}
