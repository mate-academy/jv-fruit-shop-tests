package core.basesyntax.dao.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class OperationTest {

    @Test
    void getBalanceCode() {
        //Arrange
        Operation balanceOperation = Operation.BALANCE;

        //Act
        String result = balanceOperation.getCode();

        //Assert
        assertEquals("b", result);
    }

    @Test
    void getPurchaseCode() {
        //Arrange
        Operation purchaseOperation = Operation.PURCHASE;

        //Act
        String result = purchaseOperation.getCode();

        //Assert
        assertEquals("p", result);
    }

    @Test
    void getReturnCode() {
        //Arrange
        Operation returnOperation = Operation.RETURN;

        //Act
        String result = returnOperation.getCode();

        //Assert
        assertEquals("r", result);
    }

    @Test
    void getSupplyCode() {
        //Arrange
        Operation supplyOperation = Operation.SUPPLY;

        //Act
        String result = supplyOperation.getCode();

        //Assert
        assertEquals("s", result);
    }

    @Test
    void getBalanceOperation() {
        //Arrange
        String codeOperation = "b";

        //Act
        Operation result = Operation.getOperation(codeOperation);

        //Assert
        assertEquals(Operation.BALANCE, result);
    }

    @Test
    void getPurchaseOperation() {
        //Arrange
        String codeOperation = "p";

        //Act
        Operation result = Operation.getOperation(codeOperation);

        //Assert
        assertEquals(Operation.PURCHASE, result);
    }

    @Test
    void getReturnOperation() {
        //Arrange
        String codeOperation = "r";

        //Act
        Operation result = Operation.getOperation(codeOperation);

        //Assert
        assertEquals(Operation.RETURN, result);
    }

    @Test
    void getSupplyOperation() {
        //Arrange
        String codeOperation = "s";

        //Act
        Operation result = Operation.getOperation(codeOperation);

        //Assert
        assertEquals(Operation.SUPPLY, result);
    }

    @Test
    void getNotValidOperation() {
        //Arrange
        String notValid = "q";

        //Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Operation.getOperation(notValid);
        });

        //Assert
        assertEquals("No constant with abbreviation " + notValid + " found",
                exception.getMessage());
    }

    @Test
    void validAbbreviation() {
        //Arrange
        String validAbbreviation = "s";

        //Act
        boolean result = Operation.validAbbreviation(validAbbreviation);

        //Assert
        assertTrue(result);
    }

    @Test
    void notValidAbbreviation() {
        //Arrange
        String notValidAbbreviation = "q";

        //Act
        boolean result = Operation.validAbbreviation(notValidAbbreviation);

        //Assert
        assertFalse(result);
    }
}
