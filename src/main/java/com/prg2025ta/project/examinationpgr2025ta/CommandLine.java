package com.prg2025ta.project.examinationpgr2025ta;

import core.service.UserService;

public class CliMain {

    public static void main(String[] args) {
        if (args.length == 0) {
            printHelp();
            return;
        }

        UserService userService = new UserService();

        switch (args[0]) {
            case "create-user":
                if (args.length < 2) {
                    System.out.println("Username required");
                    return;
                }
                userService.createUser(args[1]);
                System.out.println("User created");
                break;

            case "list-users":
                userService.listUsers().forEach(System.out::println);
                break;

            default:
                printHelp();
        }
    }

    private static void printHelp() {
        System.out.println("""
            Available commands:
              create-user <name>
              list-users
            """);
    }
}
