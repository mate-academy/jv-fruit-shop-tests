package core.basesyntax;

import core.basesyntax.service.impl.CsvFileReaderImpl;
import core.basesyntax.service.impl.CsvFileWriterImpl;
import core.basesyntax.service.reader.CsvFileReader;
import core.basesyntax.service.writer.CsvFileWriter;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReaderWriterTest {
    private static final String filepath = "report.csv";

    @Test
    void testReaderAndWriter_Ok() {
        String expected = "type,fruit,quantity" + System.lineSeparator()
                + "b,banana,20" + System.lineSeparator()
                + "b,apple,100" + System.lineSeparator()
                + "s,banana,100" + System.lineSeparator()
                + "p,banana,13";
        CsvFileWriter fileWriter = new CsvFileWriterImpl();
        fileWriter.writeDataToFile(filepath, expected);
        CsvFileReader fileReader = new CsvFileReaderImpl();
        List<String> strings = fileReader.readDataFromFile(filepath);
        String actual = strings.stream().collect(Collectors.joining(System.lineSeparator()));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testReaderWithNonExistingFile_NotOk() {
        CsvFileReader fileReader = new CsvFileReaderImpl();
        Assertions.assertThrows(RuntimeException.class,
                () -> fileReader.readDataFromFile("noFile.csv"));
    }

    @Test
    void writeDataToNoNameFile_NotOk() {
        CsvFileWriter writer = new CsvFileWriterImpl();
        Assertions.assertThrows(RuntimeException.class,
                () -> writer.writeDataToFile("", "data"));
    }
}
