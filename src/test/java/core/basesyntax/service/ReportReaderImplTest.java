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
    private String fileName = "src/main/resources/readTest.csv";
    private String wrongFileName = "src/main/resources/badFileName.csv";
    private List<String> lines = new ArrayList<>();

    @Before
    public void setUp() {
        lines.add("type,fruit,quantity");
        lines.add("b,banana,20");
        lines.add("b,apple,100");

        try (FileWriter writer = new FileWriter(fileName)) {
            for (String l : lines) {
                writer.write(l + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't find file by path: " + fileName, e);
        }
    }

    @After
    public void tearDown() {
        lines.clear();
        try {
            new File(fileName).delete();
            new File(wrongFileName).delete();
        } catch (Exception e) {
            throw new RuntimeException("Can't find file", e);
        }
    }

    @Test
    public void getListOfTransactions_validValues_ok() {
        lines.remove(0);
        assertEquals(lines, new ReportReaderImpl().getListOfTransactions(fileName));
    }

    @Test
    public void getListOfTransactions_invalidPFileName_notOk() {
        assertThrows(RuntimeException.class, () ->
                        new ReportReaderImpl().getListOfTransactions(wrongFileName),
                "Can't find file by path: " + wrongFileName);
    }
}
