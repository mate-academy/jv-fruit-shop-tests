package core.basesyntax.serviceimpl.readerimpl;

import core.basesyntax.strategy.serviceintrface.reader.Writer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WriterImplTest {
    private Writer fileWriter = new WriterImpl();

    @Test
    void check_path_Exception() {
        String path = "";
        String inputData = "123";
        Assertions.assertThrows(RuntimeException.class, ()
                -> fileWriter.writeReportToFile(inputData, path));
    }

    @Test
    void check_path_null_Exception() {
        String path = null;
        String inputData = "fruit,quantity \n banana,152\napple,90";
        Assertions.assertThrows(RuntimeException.class, ()
                -> fileWriter.writeReportToFile(inputData, path));

    }

    @Test
    void check_inputData_Exception() {
        String path = "src/test/resources/report.csv";
        String inputData = "";
        Assertions.assertThrows(RuntimeException.class, ()
                -> fileWriter.writeReportToFile(inputData, path));
    }

    @Test
    void check_inputData_Null_Exception() {
        String path = "src/test/resources/report.csv";
        String inputData = null;
        Assertions.assertThrows(RuntimeException.class, ()
                -> fileWriter.writeReportToFile(inputData, path));
    }
}




