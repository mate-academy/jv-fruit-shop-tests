package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface DataProcessorService {
    /**
     * Process data from CSV-file.
     *
     * @param inputData list of lines from  CSV- file.
     */

    void process(List<FruitTransaction> inputData);
}
