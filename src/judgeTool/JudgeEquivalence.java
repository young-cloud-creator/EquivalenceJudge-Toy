package judgeTool;

import java.io.File;
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
            inFormat = inFormatList[0];
        }
        else {
            inFormat = null;
        }

        if(files.length > 0) {
            this.equivalence = new UFS(this.files.length);
        } 
        else {
            this.equivalence = null;
        }
    }

    public void judge() {
        // TODO: judge the equivalence of every file pairs, files are list in this.files
    }

    public UFS getJudgeResult() {
        return this.equivalence;
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
