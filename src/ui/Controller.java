package ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

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
        // convert UFS to ArrayList so that it can be output by MainUI
        output2csv(equivalence, files, subDir);
        output2ui(equivalence, files, subDir);
    }

    private void output2csv(UFS equivalence, File[] files, File subDir) throws IOException {
        File outputDir = new File(subDir.getCanonicalPath()+"/output/");
        File equalCSV = new File(outputDir.getCanonicalPath()+"/"+"equal.csv");
        File inequalCSV = new File(outputDir.getCanonicalPath()+"/"+"inequal.csv");
        outputDir.mkdir();
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

    private void output2ui(UFS equivalence, File[] files, File subDir) throws IOException {
        ArrayList<ArrayList<String> > result = new ArrayList<>();
        boolean[] visited = new boolean[files.length];
        for(int i=0; i<visited.length; i++) {
            visited[i] = false;
        }
        for(int i=0; i<files.length; i++) {
            if(!visited[i]) {
                visited[i] = true;
                ArrayList<String> temp = new ArrayList<>();
                temp.add(files[i].getName());
                for(int j=i+1; j<files.length; j++) {
                    if(!visited[j] && equivalence.isSameRoot(i, j)) {
                        visited[j] = true;
                        temp.add(files[j].getName());
                    }
                }
                result.add(temp);
            }
        }

        ui.outputResults(subDir.getCanonicalPath(), result);
    }
}
