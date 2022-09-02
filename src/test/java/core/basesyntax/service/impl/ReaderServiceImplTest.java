package core.basesyntax.service.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReaderServiceImplTest {
    private static final String FILE_PATH = "src/test/resources/Input.csv";
    private static final List<String> EXPECTED_DATA = List.of("type,fruit,quantity", "b,banana,88");
    private static final String TEST_DATA = "type,fruit,quantity" + System.lineSeparator()
            + "b,banana,88";
    private ReaderServiceImpl readerService;

    @Before
    public void setUp() {
        readerService = new ReaderServiceImpl();
        writToFile();
    }

    @Test (expected = RuntimeException.class)
    public void readFileNotValidPath() {
        readerService.readFromFile("");
    }

    @Test
    public void readFileOk() {
        List<String> actual = readerService.readFromFile(FILE_PATH);
        Assert.assertEquals(EXPECTED_DATA, actual);
    }

    private void writToFile() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE_PATH))) {
            bufferedWriter.write(TEST_DATA);
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file: " + FILE_PATH, e);
        }
    }
}
