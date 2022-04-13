package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.ReportException;
import core.basesyntax.service.impl.CsvFileWriterServiceImpl;
import core.basesyntax.service.impl.ReportCreatorImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class CsvFileWriterServiceTest {
    private static CsvFileWriterService writer;
    private static ReportCreator reportCreator;
    private static final String OUTPUT_FILE = "src/test/resources/OutputData.csv";
    private static final String EXPECTED_FILE = "src/test/resources/ExpectedOutput.csv";

    @BeforeClass
    public static void beforeClass() {
        writer = new CsvFileWriterServiceImpl();
        reportCreator = new ReportCreatorImpl();
    }

    @Before
    public void setUp() {
        Storage.fruits.put("banana", 152);
        Storage.fruits.put("apple", 90);
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
        PrintWriter pw = new PrintWriter(OUTPUT_FILE);
        pw.close();
    }

    @Test
    public void writeToFile_properReport_Ok() throws IOException {
        writer.writeToFile(OUTPUT_FILE, reportCreator.getReport());

        byte[] file1Bytes = Files.readAllBytes(Paths.get(EXPECTED_FILE));
        byte[] file2Bytes = Files.readAllBytes(Paths.get(OUTPUT_FILE));

        String file1 = new String(file1Bytes, StandardCharsets.UTF_8);
        String file2 = new String(file2Bytes, StandardCharsets.UTF_8);

        Assertions.assertEquals(file1, file2);
    }

    @Test
    public void writeToFile_emptyReport_notOk() {
        Assertions.assertThrows(ReportException.class,
                () -> writer.writeToFile(OUTPUT_FILE, new String[0]));
    }
}
