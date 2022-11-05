package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.basesyntax.services.impl.CsvFileReaderImpl;
import com.basesyntax.services.impl.CsvFileWriterImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvFileReaderAndWriterImplTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static CsvFileReaderImpl csvFileReader;
    private static CsvFileWriterImpl csvFileWriter;
    private List<String> expectedList;
    private List<String> list;

    @BeforeAll
    static void beforeAll() {
        csvFileReader = new CsvFileReaderImpl();
        csvFileWriter = new CsvFileWriterImpl();
    }

    @BeforeEach
    public void set_up() {
        expectedList = List.of("How are u doing",
                "wass up",
                "100%N....");
    }

    @Test
    public void readFileTest_Ok() {
        list = csvFileReader.getAcceptedFileAsList("src/test/testSource/inputTestFile.csv");
        assertEquals(expectedList, list);
    }

    @Test
    public void writeIntoFileTest_Ok() {
        List<String> newExpectedList = List.of("fruit,quantity",
                "How are u doing",
                "wass up",
                "100%N....");
        String pathName = "src/test/testSource/reportTest.csv";
        csvFileWriter.createReportFile(pathName, expectedList);
        list = csvFileReader.getAcceptedFileAsList(pathName);
        assertEquals(newExpectedList, list);
    }
}
