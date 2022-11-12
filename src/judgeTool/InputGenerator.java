package judgeTool;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputGenerator {
    private final File inFormat;
    private final ArrayList<InType> inputTypes;
    private boolean knowInputTypes;

    InputGenerator(File f) {
        this.inFormat = f;
        this.inputTypes = new ArrayList<>();
        this.knowInputTypes = false;
    }

    String genInput() {
        String inputString = "";
        if(!knowInputTypes) {
            getInputTypes();
        }
        Random r = new Random();
        int exp;
        for(InType inType : inputTypes) {
            switch(inType.getInType()) {
                case INT:
                    exp = r.nextInt(inType.getUp()-inType.getLow()+1);
                    exp += inType.getLow();
                    inputString += Integer.toString(exp)+" ";
                    break;
                case CHAR:
                    exp = r.nextInt(26);
                    boolean isUpperCase = r.nextBoolean();
                    if(isUpperCase) {
                        inputString += (char)('A'+exp)+" ";
                    }
                    else {
                        inputString += (char)('a'+exp)+" ";
                    }
                    break;
                case STRING:
                    String allChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
                    exp = r.nextInt(inType.getUp()-inType.getLow()+1);
                    exp += inType.getLow();
                    for(int i=0; i<exp; i++) {
                        inputString += allChar.charAt(r.nextInt(allChar.length()));
                    }
                    inputString += " ";
                    break;
            }
        }
        return inputString;
    }

    private void getInputTypes() {
        this.knowInputTypes = true;
        if(this.inFormat == null) {
            return;
        }
        else {
            try (Scanner inScanner = new Scanner(this.inFormat)) {
                while(inScanner.hasNext()) {
                    String exp = inScanner.next();
                    InType result = parseExp(exp);
                    if(result != null) {
                        this.inputTypes.add(result);
                    }
                }
            }
            catch(FileNotFoundException e) {}
        }
    }

    private InType parseExp(String exp) {
        String intContent = "int\\((\\+|-)?[0-9]+,(\\+|-)?[0-9]+\\)";
        String charContent = "char";
        String stringContent = "string\\((\\+)?[0-9]+,(\\+)?[0-9]+\\)";
        String rangeContent = "((\\+|-)?[0-9]+)((\\+|-)?[0-9]+)";
        Pattern rangePattern = Pattern.compile(rangeContent);
        Matcher rangeMatcher = rangePattern.matcher(exp);
        rangeMatcher.find();

        if(Pattern.matches(intContent, exp)) {
            return new InType(Types.INT, Integer.parseInt(rangeMatcher.group(1)), 
                                        Integer.parseInt(rangeMatcher.group(2)));
        }
        else if(Pattern.matches(charContent, exp)) {
            return new InType(Types.CHAR, 0, 0);
        }
        else if(Pattern.matches(stringContent, exp)) {
            return new InType(Types.STRING, Integer.parseInt(rangeMatcher.group(1)), 
                                        Integer.parseInt(rangeMatcher.group(2)));
        }
        else {
            return null;
        }
    }

    private enum Types {
        INT,
        CHAR,
        STRING;
    }
    
    private class InType {

        private final Types inType;
        private final int low;
        private final int up;

        InType(Types inType, int low, int up) {
            this.inType = inType;
            this.low = low;
            this.up = up;
        }

        Types getInType() {
            return inType;
        }

        int getLow() {
            return low;
        }

        int getUp() {
            return up;
        }

    }

}
