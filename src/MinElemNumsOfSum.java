public class MinElemNumsOfSum {
  public static void main(String[] args) {
    int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    System.out.println(findElemNumsOfSum(numbers, 17));
  }

  // Find the minimum number of elements to make 'k'
  private static int findElemNumsOfSum(int[] numbers, int k) {
    int leftIndex = 0;
    int rightIndex = 1;
    int count = Integer.MAX_VALUE; // Elements count
    int sum = numbers[leftIndex] + numbers[rightIndex]; // Sum from leftIndex
    // to rightIndex

    while (true) {
      if (sum < k) { // When sum of [leftIndex to rightIndex] is smaller than k
        rightIndex++;
        if (rightIndex >= numbers.length) break;
        sum += numbers[rightIndex];
      } else { // When sum of [leftIndex to rightIndex] is bigger than k
        int tempCount = (rightIndex - leftIndex) + 1;
        if (count > tempCount) { // When new count of elements is smaller
          // than previous one, update it to new one
          count = tempCount;
        } else { // When new count of elements is bigger than previous one
          sum -= numbers[leftIndex];
          leftIndex++;
          if (leftIndex >= numbers.length) break;
        }
      }
    }
    return count;
  }
}
