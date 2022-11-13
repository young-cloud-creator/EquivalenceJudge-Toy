package judgeTool;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Execute {
    
    private File excutableFile;
    private final File outputFile;
    private final String shell;

    Execute(File codeFile, File codeDir, File outputDir) throws IOException {
        this.shell = System.getenv("SHELL");
        Process p = Runtime.getRuntime().exec(new String[]{this.shell, "-c",
            "g++ "+codeFile.getName()+" -o "+outputDir.getCanonicalPath()+"/"+
                codeFile.getName().substring(0, codeFile.getName().lastIndexOf("."))}, null, codeDir);
        outputFile = new File(outputDir.getCanonicalPath()+"/"+
                codeFile.getName().substring(0, codeFile.getName().lastIndexOf("."))+".txt");
        outputFile.createNewFile();
        try {
            if(p.waitFor() != 0) {
                excutableFile = null;
            }
            else {
                this.excutableFile = new File(outputDir.getPath()+"/"+
                codeFile.getName().substring(0, codeFile.getName().lastIndexOf(".")));
            }
        }
        catch(InterruptedException e) {
            System.out.println("Unknown error: "+e);
            System.exit(-1);
        }
    }

    void exec(String testcase) throws IOException {
        if(excutableFile == null) {
            Runtime.getRuntime().exec(new String[]{this.shell, "-c",
                "echo \"can't compile\" >> "+outputFile.getCanonicalPath()});
        }
        else {
            Process p = Runtime.getRuntime().exec(new String[]{this.shell, "-c",
                "echo \""+testcase+"\" | "+excutableFile.getCanonicalPath()+" >> "+outputFile.getCanonicalPath()});
            Runtime.getRuntime().exec(new String[]{this.shell, "-c",
                "echo \"\" >> "+outputFile.getCanonicalPath()});
            try {
                if(p.waitFor(1, TimeUnit.SECONDS)) {
                    if(p.exitValue() != 0) {
                        Runtime.getRuntime().exec(new String[]{this.shell, "-c",
                            "echo \"Nonzero Error\" >> "+outputFile.getCanonicalPath()});
                    }
                }
                else {
                    p.destroy();
                    Runtime.getRuntime().exec(new String[]{this.shell, "-c",
                            "echo \"Timeout\" >> "+outputFile.getCanonicalPath()});
                }
            }
            catch(InterruptedException e) {
                Runtime.getRuntime().exec(new String[]{this.shell, "-c",
                    "echo \"InterruptedException\" >> "+outputFile.getCanonicalPath()});
            }
        }
    }

    File getOutputFile() {
        return this.outputFile;
    }
}
