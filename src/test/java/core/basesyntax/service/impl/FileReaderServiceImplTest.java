package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.service.FileReaderService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {
    private FileReaderService fileReaderService;

    public FileReaderService getFileReaderService() {
        return fileReaderService;
    }

    public void setFileReaderService(FileReaderService fileReaderService) {
        this.fileReaderService = fileReaderService;
    }

    @BeforeEach
    void setUp() {
        fileReaderService = new FileReaderServiceImpl();
    }

    @Test
    void readFromFile_ok() throws IOException {
        File tempFile = File.createTempFile("temp", ".txt");
        String textSample = "s,orange,20\nr,apple,30\ns,banana,300\n";
        Files.write(tempFile.toPath(), textSample.getBytes());

        List<FruitTransaction> fruitTransactionList =
                fileReaderService.readFromFile(tempFile.getPath());

        Assertions.assertEquals(3, fruitTransactionList.size());

        Assertions.assertEquals(new FruitTransaction("orange", 20, Operation.SUPPLY),
                fruitTransactionList.get(0));
        Assertions.assertEquals(new FruitTransaction("apple", 30, Operation.RETURN),
                fruitTransactionList.get(1));
        Assertions.assertEquals(new FruitTransaction("banana", 300, Operation.SUPPLY),
                fruitTransactionList.get(2));
    }

    @Test
    void readFromFile_notOk() throws IOException {
        File tempFile = File.createTempFile("temp", ".txt");
        String textSample = "s,orange,20\nr,apple,30\ns,banana,300\n";
        Files.write(tempFile.toPath(), textSample.getBytes());

        List<FruitTransaction> fruitTransactionList =
                fileReaderService.readFromFile(tempFile.getPath());

        Assertions.assertNotEquals(1, fruitTransactionList.size());

        Assertions.assertNotEquals(new FruitTransaction("orange", 10, Operation.SUPPLY),
                fruitTransactionList.get(0));
        Assertions.assertNotEquals(new FruitTransaction("apple", 10, Operation.RETURN),
                fruitTransactionList.get(1));
        Assertions.assertNotEquals(new FruitTransaction("banana", 10, Operation.SUPPLY),
                fruitTransactionList.get(2));
    }

    @Test
    void readFromFile_throw() {
        String invalidFilePath = "nonexistentfile.txt";

        Assertions.assertThrows(RuntimeException.class,
                () -> fileReaderService.readFromFile(invalidFilePath));
    }
}
