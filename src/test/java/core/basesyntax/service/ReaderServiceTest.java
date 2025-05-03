package core.basesyntax.service;

import core.basesyntax.service.impl.CsvFileReaderServiceImpl;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReaderServiceTest {
    private static final String FIRST_LINE = "If I were a car";
    private static final String SECOND_LINE = "I would be a McQueen!";
    private static final String REPORT = "If I were a car"
            + System.lineSeparator() + "I would be a McQueen!";
    private static final File VALID_FILE = new File("src/test/java/resources/FromFile.csv");
    private static final File INVALID_FILE = new File("");
    private ReaderService readerService;
    private List<String> stringList;

    @BeforeEach
    void setUp() {
        readerService = new CsvFileReaderServiceImpl();
        stringList = new ArrayList<>();
        stringList.add(FIRST_LINE);
        stringList.add(SECOND_LINE);
        try {
            new FileWriter(VALID_FILE, false).close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void readDatafromTheFile_validFile_ok() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(VALID_FILE, true))) {
            bufferedWriter.write(REPORT);
        } catch (IOException e) {
            throw new RuntimeException("The file is not possible to use", e);
        }
        List<String> expected = stringList;
        List<String> actual = readerService.readDataFromTheFile(VALID_FILE);
        Assert.assertEquals(expected, actual);
    }

    @Test
    void readDatafromTheFile_fileEqualsNull_notOk() {
        Assert.assertThrows(RuntimeException.class, () ->
                readerService.readDataFromTheFile(INVALID_FILE));
    }
}
