package service.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import service.ReaderDataService;
import service.WriterDataService;

public class CsvFileWriterServiceTest {
    private static final String PATH_TO_FILE = "src/test/resources/OutputFile.csv";
    private static WriterDataService writerDataService;
    private static ReaderDataService readerDataService;
    private static StringBuilder DATA_FOR_OUTPUT;
    private static List<String> EXPECTED_DATA;

    @BeforeClass
    public static void beforeClass() {
        DATA_FOR_OUTPUT = new StringBuilder();
        EXPECTED_DATA = new ArrayList<>();
        writerDataService = new CsvFileWriterService();
        readerDataService = new CsvFileReaderService();
        DATA_FOR_OUTPUT.append("fruit,quantity").append(System.lineSeparator())
                .append("banana,152").append(System.lineSeparator())
                .append("apple,90");
        EXPECTED_DATA.add("fruit,quantity");
        EXPECTED_DATA.add("banana,152");
        EXPECTED_DATA.add("apple,90");
    }

    @Test
    public void write_toFile_Ok() {
        writerDataService.write(DATA_FOR_OUTPUT.toString(), PATH_TO_FILE);
        List<String> actual = readerDataService.read(PATH_TO_FILE);
        assertEquals(EXPECTED_DATA, actual);
    }
}
