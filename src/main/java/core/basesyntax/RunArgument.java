package core.basesyntax;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import model.FruitTransaction;
import service.ShopService;
import service.impl.ShopServiceImpl;
import strategy.Operation;

public class RunArgument {
    private static final int LENGTH = 2;
    private static final int INPUT_INDEX = 0;
    private static final int OUTPUT_INDEX = 1;

    public static void processArgumentsAndRun(String[] args) {
        if (args.length != LENGTH) {
            throw new IllegalArgumentException("Please provide input and output file names "
                + "as arguments: <inputFile> <outputFile>");
        }

        String inputFilePath = args[INPUT_INDEX];
        String outputFilePath = args[OUTPUT_INDEX];

        if (inputFilePath == null || inputFilePath.trim().isEmpty()
                || outputFilePath == null || outputFilePath.trim().isEmpty()) {
            throw new IllegalArgumentException("Input and output file names cannot be empty");
        }

        if (!Files.exists(Paths.get(inputFilePath))) {
            throw new IllegalArgumentException("Input file does not exist: " + inputFilePath);
        }

        Map<FruitTransaction.Operation, Operation> operationMap = new HashMap<>();
        ShopService shopservice = new ShopServiceImpl(operationMap);
        shopservice.run(inputFilePath, outputFilePath);
    }
}
