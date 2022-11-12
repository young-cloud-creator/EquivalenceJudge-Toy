package judgeTool;

import java.io.File;
import java.io.IOException;

public class Execute {
    
    private File excutableFile;

    Execute(File codeFile, File codeDir, File outputDir) throws IOException {
        Process p = Runtime.getRuntime().exec("g++ "+codeFile.getName()+" -o "+outputDir.getCanonicalPath()+"/"+
                codeFile.getName().substring(0, codeFile.getName().lastIndexOf(".")), null, codeDir);
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

    void exec(String testcase) {
        
    }
}
