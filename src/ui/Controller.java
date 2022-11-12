package ui;

import java.io.File;
import java.io.IOException;
import dataStructure.UFS;
import judgeTool.JudgeEquivalence;

public class Controller {

    private final MainUI ui;
    private File dir;
    private File[] subDirs;

    Controller(MainUI ui) {
        this.ui = ui;
    }

    void setDir(String dir) throws IOException {
        this.dir = new File(dir);
        if (!this.dir.isDirectory()) {
            throw new IOException("path '"+dir+"' is not a directory!");
        }

        this.subDirs = this.dir.listFiles((File f)->f.isDirectory());
    }

    void doJudge() throws IOException {
        for(File subDir : subDirs) {
            JudgeEquivalence judge = new JudgeEquivalence(subDir);
            judge.judge();
            UFS result = judge.getJudgeResult();
            processResult(result);
        }
    }

    private void processResult(UFS equivalence) {
        // TODO: process the UFS and write file pairs to .csv files
        // TODO: convert UFS to String[][] so that it can be output by MainUI
    }

}
