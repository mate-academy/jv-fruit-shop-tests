package core.basesyntax.datavalidator;

import core.basesyntax.model.Fruit;
import java.util.List;

public class OutputDataValidator {
    public void validateOutput(List<Fruit> outputData) throws DataValidationException {
        if (outputData == null || outputData.size() == 0) {
            throw new DataValidationException("Output data for the report can't be null or empty");
        }
    }
}
