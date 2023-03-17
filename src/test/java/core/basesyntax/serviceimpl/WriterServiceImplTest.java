package core.basesyntax.serviceimpl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.WriterService;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.*;

public class WriterServiceImplTest {
    File WRITE_TO = new File("src/test/java/testresources/output/write-to.csv");
    File EXPECTED_CONTENT = new File("src/test/java/testresources/output/valid-output-for-writer.csv");
    File WRONG_PATH = new File("src/test/java/testresources/wrong-path/new-file.csv");
    File EMPTY_FILE = new File("src/test/java/testresources/output/empty.csv");
    private static final String NULL_PATH = null;
    ReportService report = new ReportServiceImpl();
    WriterService writer = new WriterServiceImpl();


    @Test(expected = RuntimeException.class)
    public void writing_NullReport_notOk() {
        writer.write(WRONG_PATH, report.newReport());
    }


    @Test(expected = NullPointerException.class)
    public void write_ToNullPath_notOk() {
        writer.write(new File(NULL_PATH), "banana,152");
    }

    @Test
    public void writing_DataMustBeSame_ok() {
        Storage.storage.put("banana", 152);
        writer.write(WRITE_TO, "fruit,quantity"+ System.lineSeparator()+ "banana,152");
        try {
            List<String> actual = Files.readAllLines(WRITE_TO.toPath());
            List<String> expected = Files.readAllLines(EXPECTED_CONTENT.toPath());
            assertEquals(actual, expected);
        } catch (IOException e) {
            throw new RuntimeException("SOMEMaSSaGa", e);
        }
    }


}