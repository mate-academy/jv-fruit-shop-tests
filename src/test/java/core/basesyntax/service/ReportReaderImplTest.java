package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReportReaderImplTest {
    private static final String FILE_NAME = "src/test/resources/readTest.csv";
    private static final String WRONG_FILE_NAME = "src/test/resources/badFileName.csv";
    private List<String> lines = new ArrayList<>();
    private ReportReader reportReader = new ReportReaderImpl();

    @Before
    public void setUp() {
        lines.add("type,fruit,quantity");
        lines.add("b,banana,20");
        lines.add("b,apple,100");

        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            for (String l : lines) {
                writer.write(l + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't find file by path: " + FILE_NAME, e);
        }
    }

    @After
    public void tearDown() {
        lines.clear();
        try {
            new File(FILE_NAME).delete();
            new File(WRONG_FILE_NAME).delete();
        } catch (Exception e) {
            throw new RuntimeException("Can't find file", e);
        }
    }

    @Test
    public void getListOfTransactions_validValues_ok() {
        lines.remove(0);
        assertEquals(lines, reportReader.getListOfTransactions(FILE_NAME));
    }

    @Test
    public void getListOfTransactions_invalidPFileName_notOk() {
        assertThrows(RuntimeException.class, () ->
                        reportReader.getListOfTransactions(WRONG_FILE_NAME),
                "Can't find file by path: " + WRONG_FILE_NAME);
    }
}
