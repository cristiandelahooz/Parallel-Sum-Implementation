Implementatión de un algoritmo para sumar todos los valores de un array dividiendolo equitativamente en la cantidad de hilos/procesos indicados.

## Pasos para correr:
### Clonar
```shell
git clone https://github.com/cristiandelahooz/Parallel-Sum-Implementation
```
### Correr (java 21)
```shell
java src/main/java/Main.java
```

## Ejemplo de salida: 
<img width="461" alt="image" src="https://github.com/user-attachments/assets/18216e51-0f54-4f68-a6de-bdac1d88e147" />

## Codigo:
```java
import java.util.concurrent.ThreadLocalRandom;

public class Main {
  static final int[] NUMBER_OF_THREADS = {1, 2, 4, 8, 16, 32};

  public static void main(String[] args) {
    int[] arr = new int[1_000_000];
    populateArrayWithRandomIntegers(arr);

    showTimeBaseOnAmountOfThreads(arr);
  }

  private static void showTimeBaseOnAmountOfThreads(int[] arr) {
    for (int numberOfThread : NUMBER_OF_THREADS) {
      double initialTime = System.nanoTime();
      sumBaseOnAmountOfThreads(arr, numberOfThread);
      double finalTime = System.nanoTime();

      showMillisecondsAndNumberOfThreads((finalTime - initialTime) / 1_000_000, numberOfThread);
    }
  }

  private static void populateArrayWithRandomIntegers(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
      arr[i] = ThreadLocalRandom.current().nextInt(1, 10_000);
    }
  }

  private static void sumBaseOnAmountOfThreads(int[] arr, int amountOfThreads) {
    int[][] chunks = divideArrayBaseOnAmountOfThreads(arr, amountOfThreads);
    Thread[] threads = new Thread[amountOfThreads];
    for (int i = 0; i < amountOfThreads; i++) {
      int finalI = i;
      threads[i] = new Thread(() -> sum(chunks[finalI]));
      threads[i].start();
    }
  }

  private static int[][] divideArrayBaseOnAmountOfThreads(int[] arr, int amountOfThreads) {
    int chunkSize = (int) Math.ceil((double) arr.length / amountOfThreads);
    int[][] chunks = new int[amountOfThreads][];
    for (int i = 0; i < amountOfThreads; i++) {
      int start = i * chunkSize;
      int end = Math.min(start + chunkSize, arr.length);
      if (start < arr.length) {
        chunks[i] = new int[end - start];
        System.arraycopy(arr, start, chunks[i], 0, end - start);
      } else chunks[i] = new int[0];
    }
    return chunks;
  }

  private static void sum(int[] arr) {
    int totalSum = 0;
    for (int number : arr) {
      totalSum += number;
    }
  }

  private static void showMillisecondsAndNumberOfThreads(double finalTime, int numberOfThreads) {
    String ANSI_RESET = "\u001B[0m";
    String ANSI_GREEN = "\u001B[32m";
    String ANSI_BLUE = "\u001B[34m";

    System.out.printf(
        """
        %sFinal Time: %s%.2f ms%s | %sNumber of Threads: %s%d%s
        """,
        ANSI_BLUE,
        ANSI_GREEN,
        finalTime,
        ANSI_RESET,
        ANSI_BLUE,
        ANSI_GREEN,
        numberOfThreads,
        ANSI_RESET);
  }
}
```
