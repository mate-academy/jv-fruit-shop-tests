package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ParseService;
import core.basesyntax.service.ReadService;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.WriteService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ServiceTest {
    private static final String inputFileTest = "src/test/resources/inputDataTest.csv";
    private static final String emptyFileName = "";
    private static final String outputFileTest = "src/test/resources/reportTest.csv";

    @Test
    void readFile_inputData_ok() {
        List<String> expected = List.of("type,fruit,quantity",
                "b,orange,120",
                "b,lemon,50",
                "p,lemon,10",
                "r,lemon,2",
                "p,orange,25",
                "s,orange,55",
                "p,orange,10",
                "p,lemon,5",
                "s,lemon,30");
        ReadService reader = new ReadServiceImpl();
        List<String> actual = reader.readFile(inputFileTest);
        assertLinesMatch(expected, actual, "Test failed! You should returned next list "
                + expected + " but you returned "
                + actual.toString());
    }

    @Test
    void readFile_fileNotFound_notOk() {
        ReadService reader = new ReadServiceImpl();
        assertThrows(RuntimeException.class, () -> reader.readFile(emptyFileName));
    }

    @Test
    void parseTransactions_inputData_ok() {
        List<FruitTransaction> expected = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "orange", 120),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "lemon", 50),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "lemon",10),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "lemon",2),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "orange",25),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "orange",55),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,"orange",10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,"lemon",5),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,"lemon",30));
        ParseService parser = new ParseServiceImpl();
        List<FruitTransaction> actual = parser.parseTransactions(List.of("type,fruit,quantity",
                "b,orange,120",
                "b,lemon,50",
                "p,lemon,10",
                "r,lemon,2",
                "p,orange,25",
                "s,orange,55",
                "p,orange,10",
                "p,lemon,5",
                "s,lemon,30"));
        assertEquals(expected, actual, "Test failed! You should returned next list "
                + expected + " but you returned "
                + actual.toString());
    }

    @Test
    void getReport_fromStorage_ok() {
        List<String> expected = List.of("fruit,quantity", "orange,140", "lemon,67");
        Storage.fruits.put("orange", 140);
        Storage.fruits.put("lemon", 67);
        ReportService reportService = new ReportServiceImpl();
        List<String> actual = reportService.getReport();
        assertLinesMatch(expected, actual, "Test failed! You should returned next list "
                + expected + " but you returned "
                + actual.toString());
    }

    @Test
    void writeFile_reportList_ok() throws IOException {
        List<String> expected = List.of("fruit,quantity", "orange,140", "lemon,67");
        WriteService writer = new WriteServiceImpl();
        writer.writeFile(outputFileTest, List.of("fruit,quantity", "orange,140", "lemon,67"));
        List<String> actual = Files.readAllLines(Path.of(outputFileTest));
        assertLinesMatch(expected, actual, "Test failed! You should returned next list "
                + expected + " but you returned "
                + actual);
    }

    @Test
    void writeFile_fileNotFound_notOk() {
        WriteService writer = new WriteServiceImpl();
        assertThrows(RuntimeException.class, () ->
                writer.writeFile(emptyFileName,
                        List.of("fruit,quantity", "orange,140", "lemon,67")));
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}
