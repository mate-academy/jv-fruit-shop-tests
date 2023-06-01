package core.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.Writer;
import core.basesyntax.service.impl.WriterImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.Test;

public class WriterImplTest {

    private static final String OK_FILE_NAME = "src/test/java/core/resources/okFile.csv";
    private static final String NOT_OK_FILE_NAME = "src/tes/java/core/resources/OkFile.csv";
    private static final String NOT_FILE_PATH_NAME = "";

    @Test
    public void writeToFile_Ok() {
        Writer writer = new WriterImpl();
        writer.writeFile(prepareReport(), OK_FILE_NAME);
        File file = new File(OK_FILE_NAME);
        try {
            String actualReport = String.join(System.lineSeparator(),
                    Files.readAllLines(file.toPath()));
            assertEquals(prepareReport(), actualReport);
        } catch (IOException e) {
            throw new RuntimeException("Can`t read file " + OK_FILE_NAME);
        }
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_notExistingFilePathname_NotOk() {
        Writer writer = new WriterImpl();
        writer.writeFile(prepareReport(), NOT_OK_FILE_NAME);
    }

    @Test(expected = NullPointerException.class)
    public void writeToFile_nullFilePathname_NotOk() {
        Writer writer = new WriterImpl();
        writer.writeFile(prepareReport(), null);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_emptyFilePathname_NotOk() {
        Writer writer = new WriterImpl();
        writer.writeFile(prepareReport(), NOT_FILE_PATH_NAME);
    }

    @Test
    public void writeToFile_emptyReport_Ok() {
        Writer writer = new WriterImpl();
        writer.writeFile("fruit,quantity", OK_FILE_NAME);
        File file = new File(OK_FILE_NAME);
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
