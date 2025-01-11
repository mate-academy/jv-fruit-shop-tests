package core.basesyntax.service.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterImplTest {
    private static final String REPORT = "report";
    private static final String FILE_NAME = "src/test/resources/report.csv";
    private static final List<String> reportReadList = new ArrayList<>();
    private FileWriter fileWriter = new FileWriterImpl();
    private FileReader fileReader = new FileReaderImpl();

    @BeforeAll
    static void beforeAll() {
        reportReadList.add(REPORT);
    }

    @Test
    void write_Ok() {
        fileWriter.write(REPORT, FILE_NAME);
        assertEquals(reportReadList, fileReader.read(FILE_NAME));
    }
}