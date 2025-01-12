import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

public class PatriotSimulation {
    private static final String RADAR_DATA_FILE = "./data/radar_data.csv";
    private static final double PK_RATIO = 0.8;
    private static final int SIMULATION_STEPS = 20;

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Usage: java PatriotSimulation <interpretation>");
            System.out.println("interpretation: 0 for standard detection, 1 for alternative detection");
            return;
        }

        // Parse interpretation argument
        int interpretation;
        try {
            interpretation = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Error: interpretation must be a number");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(RADAR_DATA_FILE))) {
            String line;
            int step = 1;

            while ((line = br.readLine()) != null && step <= SIMULATION_STEPS) {
                String[] values = Arrays.stream(line.split(";"))
                        .map(value -> value.replace("\n", ""))
                        .toArray(String[]::new);

                Boolean enemyDetected = null; // Use Boolean (wrapper class) for nullability

                System.out.printf("Time step %d:%n", step);
                switch (interpretation) {
                    case 0: {
                        enemyDetected = isEnemyDetected(values);
                        // Additional logic for case 0
                        break;
                    }
                    case 1: {
                        enemyDetected = isEnemyDetectedAlternative(values);
                        // Additional logic for case 1
                        break;
                    }
                    default: {
                        // Logic for all other cases if needed
                        break;
                    }
                }

                System.out.printf("  Radar output: %s%n", Arrays.toString(values));
                System.out.printf("  Enemy detected: %s%n", enemyDetected);

                if (enemyDetected) {
                    boolean hit = launchMissile();
                    System.out.printf("  Missile launched. Hit: %s%n", hit);
                }

                System.out.println();
                step++;
            }
        } catch (IOException e) {
            System.out.println("Error reading radar data file: " + e.getMessage());
        }
    }

    private static boolean isEnemyDetectedAlternative(String[] radarOutput) {
        // Concatenate all elements of radarOutput into a single binary string
        String concatenatedBinary = String.join("", radarOutput);

        // Convert the concatenated binary string to a decimal number
        BigInteger decimalValue = new BigInteger(concatenatedBinary, 2);

        // Convert the decimal number to a string representation
        String decimalString = String.valueOf(decimalValue);

        int oddCount = 0;
        int evenCount = 0;

        for (char digit : decimalString.toCharArray()) {
            int number = Character.getNumericValue(digit);
            if (number % 2 == 0) {
                evenCount++;
            } else {
                oddCount++;
            }
        }

        return oddCount > evenCount;
    }


    private static boolean isEnemyDetected(String[] radarOutput) {
        int oddCount = 0;
        int evenCount = 0;

        for (String value : radarOutput) {
            if (value.endsWith("1")) {
                oddCount++;
            } else {
                evenCount++;
            }
        }

        return oddCount > evenCount;
    }

    private static boolean launchMissile() {
        Random random = new Random();
        return random.nextDouble() <= PK_RATIO;
    }
}