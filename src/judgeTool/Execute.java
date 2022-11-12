package judgeTool;

import java.io.File;
import java.io.IOException;

public class Execute {
    
    private File excutableFile;

    Execute(File codeFile, File codeDir, File outputDir) throws IOException, InterruptedException {
        Process p = Runtime.getRuntime().exec("g++ "+codeFile.getName()+" "+outputDir.getPath()+"/"+
                codeFile.getName().substring(0, codeFile.getName().lastIndexOf(".")), null, codeDir);
        if(p.waitFor() != 0) {
            throw new IOException("It seems like g++ not exit properly :( error code: "+p.exitValue());
        }
        this.excutableFile = new File(outputDir.getPath()+"/"+
                codeFile.getName().substring(0, codeFile.getName().lastIndexOf(".")));
    }

    void exec(String testcase) {
        
    }
}
