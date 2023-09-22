package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.servise.FileReaderUtility;
import core.basesyntax.servise.FruitTransactionService;
import core.basesyntax.servise.impl.FileReaderUtilityImp;
import core.basesyntax.servise.impl.FruitTransactionServiceImp;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class MainTest {
    @Test
    void name() {
        Main.main(new String[]{});
        String actualFile = "src/main/java/core/basesyntax/files/report "
                + LocalDate.now() + ".csv";
        try {
            BufferedReader readerActual = new BufferedReader(new FileReader(actualFile));
            StringBuilder stringBuilderActual = new StringBuilder();
            String actualLine = readerActual.readLine();
            while (actualLine != null) {
                stringBuilderActual.append(actualLine);
                actualLine = readerActual.readLine();
            }
            String expectation = "fruit,quantity" + "banana,152" + "apple,90";
            assertEquals(expectation, stringBuilderActual.toString(),
                    "Expectation :" + expectation + " Actual: "
                            + stringBuilderActual.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testReturnAndSupply_ok() throws IOException {
        String path = "src/test/java/core/basesyntax/fileTest4.csv";
        String actualFile = Main.createReportFile(path);
        String expectationFile = "src/test/java/core/basesyntax/fileTest4Exception.csv";
        BufferedReader readerActual = new BufferedReader(new FileReader(actualFile));
        StringBuilder stringBuilderActual = new StringBuilder();
        String actualLine = readerActual.readLine();
        while (actualLine != null) {
            stringBuilderActual.append(actualLine);
            actualLine = readerActual.readLine();
        }
        BufferedReader readerExpectation = new BufferedReader(new FileReader(expectationFile));
        StringBuilder stringBuilderExpectation = new StringBuilder();
        String expectationLine = readerExpectation.readLine();
        while (expectationLine != null) {
            stringBuilderExpectation.append(expectationLine);
            expectationLine = readerExpectation.readLine();
        }
        assertEquals(stringBuilderExpectation.toString(), stringBuilderActual.toString());
    }

    @Test
    void testCreateReportFile() {
        String path = "src/test/java/core/basesyntax/fileTest.csv";
        String actual = Main.createReportFile(path);
        String expectation = "src/main/java/core/basesyntax/files/report "
                + LocalDate.now() + ".csv";
        assertNotNull(actual);
        assertEquals(actual,expectation);
    }

    @Test
    void testFileReaderUtility_ok() {
        FileReaderUtility fileReader = new FileReaderUtilityImp();
        String path = "src/test/java/core/basesyntax/fileTest2.csv";
        List<String> actual = fileReader.retrieveFileData(path);
        List<String> expectation = new ArrayList<>();
        expectation.add("b,banana,20");
        expectation.add("b,apple,100");
        assertEquals(actual.size(),expectation.size());
        int index = 0;
        for (String line: actual) {
            assertEquals(line, expectation.get(index));
            index++;
        }
    }

    @Test
    void testFileReaderUtility_notOk() {
        FileReaderUtility fileReader = new FileReaderUtilityImp();
        String path = "src/test/java/core/basesyntax/fileTest3.csv";
        assertThrows(RuntimeException.class, () -> fileReader.retrieveFileData(path));
    }

    @Test
    void testFileReaderUtility_notOk1() {
        FileReaderUtility fileReader = new FileReaderUtilityImp();
        String path = "src/test/java/core/basesyntax/fileTestEmpty.csv";
        List<String> actual = fileReader.retrieveFileData(path);
    }

    @Test
    void testFruitTransactionService_NotOk() {
        FruitTransactionService fruitTransactionService = new FruitTransactionServiceImp();
        List<String> listDate = new ArrayList<String>();
        listDate.add(" , , ");
        assertThrows(IllegalArgumentException.class, () ->
                fruitTransactionService.createTransactionList(listDate));
    }
}
