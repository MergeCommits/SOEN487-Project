package com.thing.runtime;

import java.util.Scanner;

public class ConsoleClient {
    enum State {
        MainMenu,
        AlbumRepo,
        HIGH
    }

    private State currState = State.MainMenu;

    private boolean quit;

    public boolean wantsToQuit() {
        return quit;
    }

    public void setQuit(boolean quit) {
        this.quit = quit;
    }

    private Scanner keyboardScanner;

    public ConsoleClient() {
        keyboardScanner = new Scanner(System.in);
    }

    public void quit() {
        keyboardScanner.close();
    }

    public void update() {
        System.out.print("\n\n=================\n\n");

        switch (currState) {
            case MainMenu: {
                updateMainMenu();
            }
            break;

            case AlbumRepo: {
                updateAlbumRepo();
            }
            break;
        }
    }

    private void updateMainMenu() {
        System.out.print("Select an option below:\n\n");

        final int ALBUM = 1, ARTIST = 2, QUIT = 3;

        System.out.println(ALBUM + ". Album repository");
        System.out.println(ARTIST + ". Artist repository");
        System.out.println(QUIT + ". Quit application");

        int choice = 0;
        do {
            choice = getInlineInteger("\nChoice");
            switch (choice) {
                case ALBUM: {
                    currState = State.AlbumRepo;
                }
                break;

                case ARTIST: {
                    System.out.println(ARTIST);
                }
                break;

                case QUIT: {
                    setQuit(true);
                }
                break;

                default: {
                    choice = -1;
                    System.out.print("Please select a valid choice.");
                }
            }
        } while (choice < 0);
    }

    private void updateAlbumRepo() {
        System.out.print("Select an option below:\n\n");

        final int FIND = 1, LIST = 2, ADD = 3, UPDATE = 4, DELETE = 5, EXIT = 6;

        System.out.println(FIND + ". Find an album");
        System.out.println(LIST + ". List all albums");
        System.out.println(ADD + ". Add an album");
        System.out.println(UPDATE + ". Update an album");
        System.out.println(DELETE + ". Delete an album");
        System.out.println(EXIT + ". Back to main menu");

        int choice = 0;
        do {
            choice = getInlineInteger("\nChoice");
            switch (choice) {
                case FIND: {
                    System.out.println(FIND);
                }
                break;

                case LIST: {
                    System.out.println(LIST);
                }
                break;

                case ADD: {
                    System.out.println(ADD);
                }
                break;

                case UPDATE: {
                    System.out.println(UPDATE);
                }
                break;

                case EXIT: {
                    currState = State.MainMenu;
                }
                break;

                default: {
                    choice = -1;
                    System.out.print("Please select a valid choice.");
                }
            }
        } while (choice < 0);
    }

    private String getInlineString(String msg) {
        System.out.print(msg + ": ");
        return keyboardScanner.nextLine();
    }

    private int getInlineInteger(String msg) {
        Integer uh = null;
        System.out.print(msg + ": ");

        do {
            String val = keyboardScanner.nextLine();

            try {
                uh = Integer.parseInt(val);
            } catch (NumberFormatException ignored) {
                System.out.print("Please enter a valid integer number: ");
            }
        } while (uh == null);

        return uh;
    }
}
