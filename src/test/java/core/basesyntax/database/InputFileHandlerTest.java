package core.basesyntax.database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InputFileHandlerTest {
    private final InputFileHandler inputFileHandler = new InputFileHandlerImpl();
    private String filePath = "src/test/resources/input.csv";

    @Test
    void inputFileHandler_validData_Ok() {
        inputFileHandler.inputData(filePath);
    }

    @Test
    void inputFileHandler_wrongPath_Ok() {
        filePath = "wrongPath/input.csv";
        Assertions.assertThrows(RuntimeException.class, () -> inputFileHandler.inputData(filePath));
    }
}
