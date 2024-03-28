package mate.fruitshop;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import mate.fruitshop.dao.FruitTransactionDao;
import mate.fruitshop.dao.FruitTransactionDaoCsv;
import mate.fruitshop.model.FruitTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FruitTransactionDaoCsvTest {

    private static final String INPUT_FILE_NAME = "src/main/resources/input.csv";
    private static final String OUTPUT_FILE_NAME = "src/main/resources/output.csv";
    private static final String VALID_INPUT_DATA = """
            type,fruit,quantity
            b,banana,20
            b,apple,100
            s,banana,100
            p,banana,13
            r,apple,10
            p,apple,20
            p,banana,5
            s,banana,50""";

    private FruitTransactionDao dao = new FruitTransactionDaoCsv();

    @Test
    void getAll_defaultInputValues_ok() {
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50));

        writeToInputFile(VALID_INPUT_DATA);
        Assertions.assertEquals(expected, dao.getAll());
//        deleteFile(INPUT_FILE_NAME);
    }

    @Test
    void getAll_EmptyFile_ok() {
        writeToInputFile("");
        Assertions.assertEquals(Collections.emptyList(), dao.getAll());
//        deleteFile(INPUT_FILE_NAME);
    }

    @Test
    void getAll_invalidData_notOk() {
        writeToInputFile("""
                fruit,quantity
                banana,20
                apple,100""");
        Assertions.assertThrows(RuntimeException.class, dao::getAll);
        writeToInputFile("""
                type,fruit,quantity
                b,banana,
                s,,100""");
        Assertions.assertThrows(RuntimeException.class, dao::getAll);
//        deleteFile(INPUT_FILE_NAME);
    }

    @Test
    void getAll_noInputFile_notOk() {
        try {
            Files.deleteIfExists(Path.of(INPUT_FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertThrows(RuntimeException.class, dao::getAll);
    }

    @Test
    void saveReport_notExistingFile_ok() {
        try {
            Files.deleteIfExists(Path.of(OUTPUT_FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dao.saveReport(VALID_INPUT_DATA);
        Assertions.assertEquals(VALID_INPUT_DATA, readFromOutputFile());
//        deleteFile(OUTPUT_FILE_NAME);
    }

    @Test
    void saveReport_existingFile_ok() {
        writeToInputFile("some random data");
        dao.saveReport(VALID_INPUT_DATA);
        Assertions.assertEquals(VALID_INPUT_DATA, readFromOutputFile());
//        deleteFile(OUTPUT_FILE_NAME);
    }

    private void writeToInputFile(String dataToFile) {
        try {
            Files.write(Path.of(INPUT_FILE_NAME), dataToFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Can't write to file" + INPUT_FILE_NAME, e);
        }
    }

    private String readFromOutputFile() {
        try {
            return Files.readString(Path.of(OUTPUT_FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file" + OUTPUT_FILE_NAME, e);
        }
    }

    private void deleteFile(String fileName) {
        try {
            Files.delete(new File(fileName).toPath());
        } catch (IOException e) {
            throw new RuntimeException("Can't delete file " + fileName, e);
        }
    }
}
