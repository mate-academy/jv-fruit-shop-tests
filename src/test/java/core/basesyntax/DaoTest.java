package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.dao.DataConverter;
import core.basesyntax.dao.FileReader;
import core.basesyntax.dao.FileWriter;
import core.basesyntax.dao.ReportGenerator;
import core.basesyntax.dao.impl.DataConverterImpl;
import core.basesyntax.dao.impl.FileReaderImpl;
import core.basesyntax.dao.impl.FileWriterImpl;
import core.basesyntax.dao.impl.ReportGeneratorImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DaoTest {
    private FileReader fileReader;
    private FileWriter fileWriter;
    private DataConverter dataConverter;
    private ReportGenerator reportGenerator;
    private FruitStorage fruitStorage;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
        fileWriter = new FileWriterImpl();
        dataConverter = new DataConverterImpl();
        fruitStorage = new FruitStorage();
        reportGenerator = new ReportGeneratorImpl(fruitStorage);
    }

    @Test
    void read_validFile_Ok() {
        List<String> result = fileReader.read("src/test/validFile.csv");
        String joinedResult = String.join(", ", result);
        assertEquals("BALANCE,apple,100, SUPPLY,banana,50", joinedResult);
    }

    @Test
    void read_nonExistentFile_NotOk() {
        assertThrows(RuntimeException.class, () -> fileReader
                .read("nonexistentfolder/nonexistentfile.csv"));
    }

    @Test
    void writeToFile_ok() {
        String expectedContent = "BALANCE,apple,100, SUPPLY,banana,50";
        String filePath = "src/test/validfile.csv";
        fileWriter.write(expectedContent, filePath);

        List<String> result = fileReader.read(filePath);
        String joinedResult = String.join(", ", result);
        assertEquals("BALANCE,apple,100, SUPPLY,banana,50", joinedResult);
    }

    @Test
    void write_toNonExistentFile_NotOk() {
        assertThrows(RuntimeException.class, () -> fileWriter
                .write("BALANCE,apple,100, SUPPLY,banana,50",
                        "nonexistentfolder/nonexistentfile.csv"));
    }

    @Test
    void convertData_Ok() {
        List<String> report = Arrays.asList("BALANCE,apple,100", "SUPPLY,banana,50");
        List<FruitTransaction> result = dataConverter.convertToTransaction(report);

        assertEquals(2, result.size());
        assertEquals(new FruitTransaction(Operation.BALANCE, "apple", 100), result.get(0));
        assertEquals(new FruitTransaction(Operation.SUPPLY, "banana", 50), result.get(1));
    }

    @Test
    void convert_toWrongFormat_notOk() {
        List<String> report = Arrays.asList("BALANCE,apple", "SUPPLY,banana,50");

        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(report));
    }

    @Test
    void convert_illegalOperation_notOK() {
        List<String> report = Arrays.asList("NONEXISTENTOPERATION,apple,100", "SUPPLY,banana,50");

        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(report));
    }

    @Test
    void convert_illegalQuantity_notOK() {
        List<String> report = Arrays.asList("BALANCE,apple,-20", "SUPPLY,banana,50");

        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(report));
    }

    @Test
    void convert_illegalQuantityFormat_notOK() {
        List<String> report = Arrays.asList("BALANCE,apple,20.00", "SUPPLY,banana,50");

        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(report));
    }

    @Test
    void generateReport_Ok() {
        fruitStorage.addFruit("apple", 100);
        fruitStorage.addFruit("banana", 50);
        fruitStorage.addFruit("peach", 10);

        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "apple,100" + System.lineSeparator()
                + "banana,50" + System.lineSeparator()
                + "peach,10" + System.lineSeparator();

        String actualResult = reportGenerator.getReport();

        assertEquals(expectedReport, actualResult);
    }
}
