package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;

public class StorageDaoImplTest {
    private static final String DEFAULT_DATA = "b,banana,20" + System.lineSeparator()
            + "b,apple,100" + System.lineSeparator()
            + "s,banana,100" + System.lineSeparator()
            + "p,banana,13" + System.lineSeparator()
            + "r,apple,10" + System.lineSeparator()
            + "p,apple,20" + System.lineSeparator()
            + "p,banana,5" + System.lineSeparator()
            + "s,banana,50";

    private static final String DEFAULT_DATA_TO_WRITE = "b,banana,100" + System.lineSeparator()
            + "b,apple,60";
    private static final String FILE_NAME = "src/test/resources/forDaoTests.csv";
    private StorageDao storageDao;

    @Before
    public void setUp() throws Exception {
        storageDao = new StorageDaoImpl();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE_NAME))) {
            bufferedWriter.write(DEFAULT_DATA);
        } catch (IOException e) {
            throw new RuntimeException("Can`t write data to file " + FILE_NAME, e);
        }
    }

    @Test
    public void storageDaoImpl_equalsReadDataFromFile_Ok() {
        assertTrue(isEqualsDataFromFile(DEFAULT_DATA));
    }

    @Test
    public void storageDaoImpl_equalsWriteDataToFile_Ok() {
        storageDao.writeData(FILE_NAME, DEFAULT_DATA_TO_WRITE);
        assertTrue(isEqualsDataFromFile(DEFAULT_DATA_TO_WRITE));
    }

    private boolean isEqualsDataFromFile(String expected) {
        String actual = storageDao.readData(FILE_NAME)
                .stream()
                .collect(Collectors.joining(System.getProperty("line.separator")));

        return expected.equals(actual);
    }
}
