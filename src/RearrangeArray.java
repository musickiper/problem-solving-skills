import java.util.*;

public class RearrangeArray {
    public static void main(String[] args) {

    }

    // Rearrange an array of characters so that
    // no two adjacent characters are identical.
    public static char[] rearrange(char[] origin) {
        Map<Character, Integer> map = new HashMap<>(); // {char, frequency of char}

        // Convert original char array to map
        for (char c : origin) {
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }

        List<Character> keys = new LinkedList<>(map.keySet());
        List<Integer> values = new LinkedList<>(map.values());
        ArrayList<Character> output = new ArrayList<>();

        while (true) {
            int[] indexes = findMaxAndSecMaxAndMin(values); // [index of max frequency, index of secMax frequency, index of min frequency]
            if (values.get(indexes[0]).equals(values.get(indexes[1]))
                    && values.get(indexes[1]).equals(values.get(indexes[2]))) { // When all the characters has the same frequency, break while loop
                break;
            } else {
                output.add(keys.get(indexes[0])); // The most frequent character
                values.set(indexes[0], values.get(indexes[0]) - 1);
                output.add(keys.get(indexes[1])); // The second most frequent character
                values.set(indexes[1], values.get(indexes[1]) - 1);
            }
        }

        // Attach all left chars to output
        while (values.get(0) != 0) {
            for (char key : keys) {
                output.add(key);
            }
            values.set(0, values.get(0) - 1);
        }

        // Convert output list to array
        char[] temp = new char[output.size()];
        for (int i = 0; i < output.size(); i++) {
            temp[i] = output.get(i);
        }

        return temp;
    }

    // Find max, second max, min frequent characters' indexes
    public static int[] findMaxAndSecMaxAndMin(List<Integer> numbers) {
        int max = 0;
        int secMax = 0;
        int min = 0;

        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i) < numbers.get(min)) {
                min = i;
            } else if (numbers.get(i) > numbers.get(secMax) && numbers.get(i) < numbers.get(max)) {
                secMax = i;
            } else if (numbers.get(i) > max) {
                secMax = max;
                max = i;
            }
        }
        return new int[]{max, secMax, min};
    }
}
