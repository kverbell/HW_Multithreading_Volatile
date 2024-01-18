import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        AtomicInteger threeChars = new AtomicInteger();
        AtomicInteger fourChars = new AtomicInteger();
        AtomicInteger fiveChars = new AtomicInteger();

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread thread1 = new Thread(() -> {
            for (String s : texts) {
                incrementCounters(isPalindrome(s), threeChars, fourChars, fiveChars);
            }
        });

        thread1.start();
        thread1.join();

        Thread thread2 = new Thread(() -> {
            for (String s : texts) {
                incrementCounters(isSimilarLetters(s), threeChars, fourChars, fiveChars);
            }
        });

        thread2.start();
        thread2.join();

        Thread thread3 = new Thread(() -> {
            for (String s : texts) {
                incrementCounters(isIncreasingLetters(s), threeChars, fourChars, fiveChars);
            }
        });

        thread3.start();
        thread3.join();

        System.out.println("Красивых слов с длиной 3: " + threeChars + " шт\n" +
                "Красивых слов с длиной 4: " + fourChars + " шт\n" +
                "Красивых слов с длиной 5: " + fiveChars + " шт");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static int isPalindrome(String s) {
        char[] sCharArray = s.toCharArray();
        int left = 0;
        int right = sCharArray.length - 1;
        int match = 0;

        while (left < right) {
            if (sCharArray[left] == sCharArray[right]) {
                match++;
                left++;
                right--;
            } else {
                match = 0;
                break;
            }
        }

        if (match > 0) {
            return sCharArray.length;
        } else {
            return 0;
        }

    }

    public static int isSimilarLetters(String s) {
        char[] sCharArray = s.toCharArray();
        int match = 0;
        for (int i = 1; i < sCharArray.length; i++) {
            if (sCharArray[i] == sCharArray[i - 1]) {
                match++;
            } else {
                match = 0;
                break;
            }
        }

        if (match > 0) {
            return sCharArray.length;
        } else {
            return 0;
        }
    }

    public static int isIncreasingLetters(String s) {
        char[] sCharArray = s.toCharArray();
        int match = 0;

        if (isSimilarLetters(s) == 0) {
            for (int i = 1; i < sCharArray.length; i++) {
                if (sCharArray[i] >= sCharArray [i - 1]) {
                    match++;
                } else {
                    match = 0;
                    break;
                }
            }
        }

        if (match > 0) {
            return sCharArray.length;
        } else {
            return 0;
        }
    }

    public static void incrementCounters(int textChecking, AtomicInteger threeChars,
                                         AtomicInteger fourChars, AtomicInteger fiveChars) {
        switch (textChecking) {
            case 3 -> threeChars.incrementAndGet();
            case 4 -> fourChars.incrementAndGet();
            case 5 -> fiveChars.incrementAndGet();
        }
    }
}
