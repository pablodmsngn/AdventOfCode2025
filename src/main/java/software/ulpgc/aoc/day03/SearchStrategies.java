package software.ulpgc.aoc.day03;

public class SearchStrategies {

    public static long Greedy(String sequence, int n) {
        if (sequence == null || n <= 0 || n > sequence.length()) {
            return 0;
        }
        StringBuilder result = new StringBuilder();
        int lastPosition = -1;
        for (int i = 0; i < n; i++) {
            int missingDigits = n - 1 - i;
            int searchLimit = sequence.length() - missingDigits;

            int bestDigit = -1;
            int bestDigitPosition = -1;

            for (int j = lastPosition + 1; j < searchLimit; j++) {
                int currentDigit = Character.getNumericValue(sequence.charAt(j));


                if (currentDigit == 9) {
                    bestDigit = 9;
                    bestDigitPosition = j;
                    break;
                }

                if (currentDigit > bestDigit) {
                    bestDigit = currentDigit;
                    bestDigitPosition = j;
                }
            }

            if (bestDigitPosition != -1) {
                result.append(bestDigit);
                lastPosition = bestDigitPosition;
            }
        }

        return !result.isEmpty() ? Long.parseLong(result.toString()) : 0L;
    }
}