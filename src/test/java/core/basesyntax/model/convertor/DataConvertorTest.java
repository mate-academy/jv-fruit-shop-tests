package core.basesyntax.model.convertor;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.fao.CustomFileReader;
import core.basesyntax.fao.CustomFileReaderImpl;
import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DataConvertorTest {
    private static final String FILE_TO_READ = "reportToRead.csv";
    private static final String TEST_FILE = "forConvertor.csv";
    private static CustomFileReader fileReader;
    private static DataConvertor dataConvertor;

    @BeforeAll
    static void beforeAll() {
        fileReader = new CustomFileReaderImpl();
        dataConvertor = new DataConvertorImpl();
    }

    @Test
    void convertorOk() {
        List<String> read = fileReader.read(FILE_TO_READ);
        List<FruitTransaction> fruitTransactions = dataConvertor.convertToTransaction(read);
        assertNotNull(fruitTransactions);
    }

    @Test
    void convertorNotOK() {
        List<String> read = fileReader.read(TEST_FILE);
        assertThrows(IllegalArgumentException.class, () ->
                dataConvertor.convertToTransaction(read));
    }
}
