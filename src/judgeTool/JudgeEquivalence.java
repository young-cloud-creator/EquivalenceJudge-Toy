package judgeTool;

import java.io.File;
import java.io.IOException;

import dataStructure.UFS;

public class JudgeEquivalence {

    private final File dir;
    private final File[] files;
    private final File inFormat;
    private final UFS equivalence;

    public JudgeEquivalence(File dir) {
        this.dir = dir;
        this.files = dir.listFiles((File f)-> (getFileExtension(f).equalsIgnoreCase("cpp") 
                                            || getFileExtension(f).equalsIgnoreCase("c")));
        final String inFormatName = "stdin_format.txt";
        File[] inFormatList = this.dir.listFiles((File f)-> (f.getName().equalsIgnoreCase(inFormatName)));

        if(inFormatList.length > 0) {
            this.inFormat = inFormatList[0];
        }
        else {
            this.inFormat = null;
        }

        if(this.files.length > 0) {
            this.equivalence = new UFS(this.files.length);
        } 
        else {
            this.equivalence = null;
        }
    }

    public void judge() throws IOException {
        // judge the equivalence of every file pairs, files are list in this.files
        final int testcasesNum = 10;
        InputGenerator inputGenerator = new InputGenerator(this.inFormat);
        String[] testcases = new String[testcasesNum];
        for(int i=0; i<testcasesNum; i++) {
            testcases[i] = inputGenerator.genInput();
        }
        Execute[] executables = new Execute[files.length];
        File outputDir = new File(this.dir.getPath()+"/temp");
        outputDir.mkdir();
        for(int i=0; i<files.length; i++) {
            executables[i] = new Execute(this.files[i], this.dir, outputDir);
        }
    }

    public UFS getJudgeResult() {
        return this.equivalence;
    }

    public File[] getFiles() {
        return this.files;
    }

    private String getFileExtension(File f) {
        String extension = new String();
        int idx = f.getName().lastIndexOf(".");
        if(idx != -1) {
            extension = f.getName().substring(idx+1);
        }
        return extension;
    }
}
