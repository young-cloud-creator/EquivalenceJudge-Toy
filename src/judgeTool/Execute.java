package judgeTool;

import java.io.File;
import java.io.IOException;

public class Execute {
    
    private File excutableFile;
    private File outputFile;

    Execute(File codeFile, File codeDir, File outputDir) throws IOException {
        Process p = Runtime.getRuntime().exec("g++ "+codeFile.getName()+" -o "+outputDir.getCanonicalPath()+"/"+
                codeFile.getName().substring(0, codeFile.getName().lastIndexOf(".")), null, codeDir);
        outputFile = new File(codeFile.getName()+" -o "+outputDir.getCanonicalPath()+"/"+
                codeFile.getName().substring(0, codeFile.getName().lastIndexOf("."))+".txt");
        outputFile.createNewFile();
        try {
            if(p.waitFor() != 0) {
                excutableFile = null;
            }
        }
        catch(InterruptedException e) {
            System.out.println("Unknown error: "+e);
            System.exit(-1);
        }
        this.excutableFile = new File(outputDir.getPath()+"/"+
                codeFile.getName().substring(0, codeFile.getName().lastIndexOf(".")));
    }

    void exec(String testcase) throws IOException {
        Process p = Runtime.getRuntime().exec("echo \""+testcase+"\" | "+excutableFile.getCanonicalPath()+
                    " >> "+outputFile.getCanonicalPath(), null);
        Runtime.getRuntime().exec("echo \"\" >> "+outputFile.getCanonicalPath());
        try {
            if(p.waitFor() != 0) {
                Runtime.getRuntime().exec("echo \"Error: \"" +p.exitValue()+ " >> "+outputFile.getCanonicalPath());
            }
        }
        catch(InterruptedException e) {
            Runtime.getRuntime().exec("echo \"InterruptedException\" >> "+outputFile.getCanonicalPath());
        }
    }
}
