package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.Test;

public class WriterCsvImplTest {
    private static final String RESOURCES_DIRECTORY = "src/test/resources/";
    private static final String EMPTY_FILE_PATHNAME = RESOURCES_DIRECTORY + "";
    private static final String OK_FILE_PATHNAME = RESOURCES_DIRECTORY
            + "reportOkFilePathname.csv";
    private static final String NOT_EXISTING_FILE_PATHNAME = "src/test1/resources/"
            + "notExistingFilePathname.csv";

    @Test
    public void writeToFile_Ok() {
        Writer writer = new WriterCsvImpl();
        writer.writeToFile(prepareReport(), OK_FILE_PATHNAME);
        File file = new File(OK_FILE_PATHNAME);
        try {
            String actualReport = String.join(System.lineSeparator(),
                    Files.readAllLines(file.toPath()));
            assertEquals(prepareReport(), actualReport);
        } catch (IOException e) {
            throw new RuntimeException("Can`t read file", e);
        }
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_notExistingFilePathname_NotOk() {
        Writer writer = new WriterCsvImpl();
        writer.writeToFile(prepareReport(), NOT_EXISTING_FILE_PATHNAME);
    }

    @Test(expected = NullPointerException.class)
    public void writeToFile_nullFilePathname_NotOk() {
        Writer writer = new WriterCsvImpl();
        writer.writeToFile(prepareReport(),null);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_emptyFilePathname_NotOk() {
        Writer writer = new WriterCsvImpl();
        writer.writeToFile(prepareReport(), EMPTY_FILE_PATHNAME);
    }

    @Test
    public void writeToFile_emptyReport_Ok() {
        Writer writer = new WriterCsvImpl();
        writer.writeToFile("fruit,quantity", OK_FILE_PATHNAME);
        File file = new File(OK_FILE_PATHNAME);
        try {
            String actualReport = String.join(System.lineSeparator(),
                    Files.readAllLines(file.toPath()));
            assertEquals("fruit,quantity", actualReport);
        } catch (IOException e) {
            throw new RuntimeException("Can`t read file", e);
        }
    }

    private String prepareReport() {
        return "fruit,quantity" + System.lineSeparator() + "banana,152"
                + System.lineSeparator() + "apple,90";
    }
}
