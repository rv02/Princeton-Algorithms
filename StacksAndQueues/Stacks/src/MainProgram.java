import java.nio.file.Paths;
import java.util.Scanner;

public class MainProgram {
    public static void main(String[] args) {
        ResizingArrayStackOfStrings stack = new ResizingArrayStackOfStrings();
        try (Scanner scanner = new Scanner(Paths.get("input.txt"))) {
            while (scanner.hasNextLine()) {
                String item = scanner.nextLine();
                if (!item.equals("-")) {
                    stack.push(item);
                } else if (!stack.isEmpty()) {
                    System.out.println(stack.pop() + " ");
                }
            }
            System.out.println();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}
