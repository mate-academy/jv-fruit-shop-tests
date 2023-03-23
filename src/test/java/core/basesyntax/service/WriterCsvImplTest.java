package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.Before;
import org.junit.Test;

public class WriterCsvImplTest {
    private static final String RESOURCES_DIRECTORY = "src/test/resources/";
    private static final String EMPTY_FILE_PATHNAME = RESOURCES_DIRECTORY + "";
    private static final String OK_FILE_PATHNAME = RESOURCES_DIRECTORY
            + "reportOkFilePathname.csv";
    private static final String NOT_EXISTING_FILE_PATHNAME = "src/test1/resources/"
            + "notExistingFilePathname.csv";
    private static String report;
    private static Writer writer;
    private StringBuilder stringBuilder;

    @Before
    public void setUp() throws Exception {
        writer = new WriterCsvImpl();
        stringBuilder = new StringBuilder("fruit,quantity");
    }

    @Test
    public void writeToFile_Ok() {
        stringBuilder.append(System.lineSeparator()).append("banana,152")
                .append(System.lineSeparator()).append("apple,90");
        report = stringBuilder.toString();
        writer.writeToFile(report, OK_FILE_PATHNAME);
        File file = new File(OK_FILE_PATHNAME);
        try {
            String actualReport = String.join(System.lineSeparator(),
                    Files.readAllLines(file.toPath()));
            assertEquals(report, actualReport);
        } catch (IOException e) {
            throw new RuntimeException("Can`t read file", e);
        }
    }

    @Test
    public void writeToFile_notExistingFilePathname_NotOk() {
        stringBuilder.append(System.lineSeparator()).append("banana,152")
                .append(System.lineSeparator()).append("apple,90");
        report = stringBuilder.toString();
        assertThrows(RuntimeException.class, () -> writer.writeToFile(report,
                NOT_EXISTING_FILE_PATHNAME));
    }

    @Test
    public void writeToFile_nullFilePathname_NotOk() {
        stringBuilder.append(System.lineSeparator()).append("banana,152")
                .append(System.lineSeparator()).append("apple,90");
        report = stringBuilder.toString();
        assertThrows(RuntimeException.class, () -> writer.writeToFile(report,null));
    }

    @Test
    public void writeToFile_emptyFilePathname_NotOk() {
        stringBuilder.append(System.lineSeparator()).append("banana,152")
                .append(System.lineSeparator()).append("apple,90");
        report = stringBuilder.toString();
        assertThrows(RuntimeException.class, () -> writer.writeToFile(report, EMPTY_FILE_PATHNAME));
    }

    @Test
    public void writeToFile_emptyReport_Ok() {
        report = stringBuilder.toString();
        writer.writeToFile(report, OK_FILE_PATHNAME);
        File file = new File(OK_FILE_PATHNAME);
        try {
            String actualReport = String.join(System.lineSeparator(),
                    Files.readAllLines(file.toPath()));
            assertEquals(report, actualReport);
        } catch (IOException e) {
            throw new RuntimeException("Can`t read file", e);
        }
    }
}
