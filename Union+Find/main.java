import java.util.Scanner;

public class main {
    
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a value: ");
        improvedQU qu = new improvedQU(Integer.valueOf(scanner.nextLine()));
        while (true) {
            System.out.print("Command: ");
            String command = scanner.nextLine();
            if (command.equals("")) {
                break;
            }
            System.out.print("Enter index 1: ");
            int p = Integer.valueOf(scanner.nextLine());
            System.out.print("Enter index 2: ");
            int q = Integer.valueOf(scanner.nextLine());
            if (command.equals("union")) {
                qu.union(p, q);
            } else {
                System.out.println(qu.connected (p, q));
            }
        }

    }
}
