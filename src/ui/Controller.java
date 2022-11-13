package ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

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
            UFS resultUFS = judge.getJudgeResult();
            File[] resultFiles = judge.getFiles();
            processResult(resultUFS, resultFiles, subDir);
        }
    }

    private void processResult(UFS equivalence, File[] files, File subDir) throws IOException {
        // process the UFS and write file pairs to .csv files
        // TODO: convert UFS to String[][] so that it can be output by MainUI
        output2csv(equivalence, files, subDir);
    }

    private void output2csv(UFS equivalence, File[] files, File subDir) throws IOException {
        File equalCSV = new File(subDir.getCanonicalPath()+"/"+"equal.csv");
        File inequalCSV = new File(subDir.getCanonicalPath()+"/"+"inequal.csv");
        equalCSV.createNewFile();
        inequalCSV.createNewFile();
        FileOutputStream equalFos = new FileOutputStream(equalCSV);
        FileOutputStream inequalFos = new FileOutputStream(inequalCSV);
        OutputStreamWriter equalWriter = new OutputStreamWriter(equalFos);
        OutputStreamWriter inequalWriter = new OutputStreamWriter(inequalFos);

        for(int i=0; i<files.length; i++) {
            for(int j=i+1; j<files.length; j++) {
                if(equivalence.isSameRoot(i, j)) {
                    equalWriter.write(files[i].getCanonicalPath()+","+files[j].getCanonicalPath()+"\n");
                }
                else {
                    inequalWriter.write(files[i].getCanonicalPath()+","+files[j].getCanonicalPath()+"\n");
                }
            }
        }

        equalWriter.close();
        inequalWriter.close();
    }

}
