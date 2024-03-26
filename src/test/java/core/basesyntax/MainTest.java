//package core.basesyntax;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import core.basesyntax.readandwriteimpl.CsvReaderImpl;
//import java.io.File;
//import java.util.List;
//import org.junit.jupiter.api.Test;
//
//class MainTest {
//    @Test
//    void main_createsOutputFileWithCorrectContent() {
//        Main.main(new String[]{});
//
//        File outputFile = new File("test.csv");
//        assertTrue(outputFile.exists());
//
//        List<String> lines = new CsvReaderImpl()
//                .readDataFromFile("test.csv");
//
//        assertEquals("fruit,quantity", String.join("\n", lines));
//    }
//}
