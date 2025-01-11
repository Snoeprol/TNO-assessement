package tno.radar;

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
    private static final int INTERPRETATION = 1;

    public static void run() {
        try (BufferedReader br = new BufferedReader(new FileReader(RADAR_DATA_FILE))) {
            String line;
            int step = 1;

            while ((line = br.readLine()) != null && step <= SIMULATION_STEPS) {
                String[] values = line.trim().split(";");

                Boolean enemyDetected = null;

                System.out.printf("Time step %d:%n", step);
                switch (INTERPRETATION) {
                    case 0: {
                        enemyDetected = isEnemyDetected(values);
                        break;
                    }
                    case 1: {
                        enemyDetected = isEnemyDetectedAlternative(values);
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

        // Count odd and even digits in the decimal string
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