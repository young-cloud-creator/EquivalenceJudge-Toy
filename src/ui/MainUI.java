package ui;
import java.io.IOException;
import java.util.Scanner;

public class MainUI {

    private final Controller controller;
    private final Scanner in;

    private MainUI() {
        this.controller = new Controller(this);
        this.in = new Scanner(System.in);
    }

    public static void main(String[] args) {
        MainUI mainUI = new MainUI();
        mainUI.clearUI();
        boolean fine = false;

        while(!fine) {
            try {
                mainUI.showUI();
                mainUI.listenInput();
                fine = true;
            } catch (IOException e) {
                mainUI.clearUI();
                System.out.println(e.getMessage());
                fine = false;
            }
        }
    }

    private void showUI() {
        System.out.print("Please type your target path here: ");
    }

    private void clearUI() {
        System.out.print("\033\143");
        System.out.flush();
    }

    private void listenInput() throws IOException {
        String targetPath = in.nextLine();
        controller.setDir(targetPath);
        in.close();
        controller.doJudge();
    }

    void outputResults(String pathName, String[][] equivalence) {
        // pathName stores path to files, equivalence[i] stores files equivalence with each other
        // TODO: output the result to stdout
    }
}
