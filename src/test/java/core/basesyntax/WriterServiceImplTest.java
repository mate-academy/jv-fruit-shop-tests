package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WriterServiceImplTest {
    private static final Path FILE_PATH = Path.of("src/test/resources/testWriteFile.csv");
    private static final String DATA_TO_WRITE = "product,amount";
    private static final Integer FIRST_FILE_LINE = 0;
    private WriterServiceImpl writerService;

    @BeforeEach
    public void setUp() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void writeReport_nullData_notOk() {
        assertThrows(RuntimeException.class,
                () -> writerService.writeReport(null, FILE_PATH.toString()));
    }

    @Test
    public void writeReport_correctData_ok() {
        writerService.writeReport(DATA_TO_WRITE, FILE_PATH.toString());
        try {
            List<String> readData = Files.readAllLines(FILE_PATH);
            assertEquals(DATA_TO_WRITE, readData.get(FIRST_FILE_LINE));
        } catch (IOException e) {
            throw new RuntimeException("Can`t read data from file " + FILE_PATH, e);
        }
    }
}
