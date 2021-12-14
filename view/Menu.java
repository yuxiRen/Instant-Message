package view;

public class Menu {
    private boolean loop = true;
    private String userInput = "";

    private void menu() {
        while (loop) {
            System.out.println("========Welcome to Instant Message===========");
            System.out.println("\t\t 1 Login");
            System.out.println("\t\t 2 Logout");
        }
    }
}
