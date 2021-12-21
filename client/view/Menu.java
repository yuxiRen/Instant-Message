package view;

import service.ClientService;
import service.Communication;
import util.Utility;

public class Menu {
    private boolean loop = true;
    private String userInput = "";
    private ClientService userClient = new ClientService();
    private Communication communication = new Communication();

    public static void main(String[] args) {
        new Menu().menu();
        System.out.println("========Exit Instant Message!========");
    }

    private void menu() {
        while (loop) {
            System.out.println("========Welcome to Instant Message========");
            System.out.println("\t\t 1 Login");
            System.out.println("\t\t 2 Logout");
            System.out.println("Enter your command: ");
            userInput = Utility.readString(1);
            switch (userInput) {
                case "1":
                    System.out.println("Enter your ID: ");
                    String id = Utility.readString(50);
                    System.out.println("Enter your password: ");
                    String pwd = Utility.readString(50);
                    if (userClient.checkUser(id, pwd)) {
                        System.out.println("========Welcome " + id + "========");
                        while (loop) {
                            System.out.println("========" + id + "'s message window========");
                            System.out.println("\t\t 1 Show Online Users");
                            System.out.println("\t\t 2 Group Message");
                            System.out.println("\t\t 3 Private message");
                            System.out.println("\t\t 4 Send File");
                            System.out.println("\t\t 9 Exit");
                            System.out.println("Enter your command: ");
                            userInput = Utility.readString(1);
                            switch (userInput) {
                                case "1":
                                    userClient.onlineUsersList();
                                case "2":
                                    System.out.println("Group Message");
                                    break;
                                case "3":
                                    System.out.println("Who do you want to talk to?");
                                    String receiverId = Utility.readString(50);
                                    System.out.println("Type your message here: ");
                                    String content = Utility.readString(100);
                                    communication.sendContent(content, id, receiverId);
                                    break;
                                case "4":
                                    System.out.println("Send File");
                                    break;
                                case "9":
                                    userClient.logout();
                                    loop = false;
                                    break;
                            }
                        }
                    } else {
                        System.out.println("========Login Failed========");
                    }
                    break;
                case "9":
                    loop = false;
                    break;
            }
        }
    }
}
