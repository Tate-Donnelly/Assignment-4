package converter;

import converter.exceptions.MalformedNumberException;
import converter.exceptions.ValueOutOfBoundsException;

import java.util.Scanner;

/**
 * This class implements a converter that takes a string that represents a number in either the
 * Elbonian or Arabic numeral form. This class has methods that will return a value in the chosen form.
 *
 * @version 3/18/17
 */

public class ElbonianArabicConverter {
    // A string that holds the number (Elbonian or Arabic) you would like to convert
    private final String number;

    /**
     * Constructor for the ElbonianArabic class that takes a string. The string should contain a valid
     * Elbonian or Arabic numeral. The String can have leading or trailing spaces. But there should be no
     * spaces within the actual number (ie. "9 9" is not ok, but " 99 " is ok). If the String is an Arabic
     * number it should be checked to make sure it is within the Elbonian number systems bounds. If the
     * number is Elbonian, it must be a valid Elbonian representation of a number.
     *
     * @param number A string that represents either a Elbonian or Arabic number.
     * @throws ValueOutOfBoundsException Thrown if the value is an Arabic integer that cannot be represented
     * in the Elbonian number system.
     * @throws MalformedNumberException Thrown if the value is an Elbonian number that does not conform
     * to the rules of the Elbonian number system or any other error in Arabic number input.
	 * Leading and trailing spaces should not throw an error.
     */
    public ElbonianArabicConverter(String number) throws MalformedNumberException, ValueOutOfBoundsException {
        // TODO check to see if the number is valid, then set it equal to the string
        this.number = number;
        
    }

    /**
     * Converts the number to an Arabic numeral or returns the current value as an int if it is already
     * in the Arabic form.
     *
     * @return An arabic value
     */
    public int toArabic() throws MalformedNumberException, ValueOutOfBoundsException  {
        checkForErrorsArabic();
        
        char[] charArray = number.toCharArray();
        int total = 0;
        for(char ch : charArray){
            switch(ch){
                case 'N':
                    total+=3000;
                    break;
                case 'M':
                    total+=1000;
                    break;
                case 'D':
                    total+=300;
                    break;
                case 'C':
                    total+=100;
                    break;
                case 'L':
                    total+=30;
                    break;
                case 'X':
                    total+=10;
                    break;
                case 'V':
                    total+=3;
                    break;
                case 'I':
                    total+=1;
                    break;
                case 'Z':
                    break;
            }
        }
        return total;
    }

    /**
     * Converts the number to an Elbonian numeral or returns the current value if it is already in the Elbonian form.
     *
     * @return An Elbonian value
     */
    boolean negative=false;
    public String toElbonian() throws MalformedNumberException, ValueOutOfBoundsException {
        checkForErrorsElbonian();
        if(isInteger(number)) {
            if (number.charAt(0) == '-' && !negative) {
                System.out.println("It's negative");
                negative = true;
                return toElbonian();
            } else if (Integer.parseInt(number) > 3000) {//N
                return "N" + toElbonian();
            } else if (Integer.parseInt(number) > 1000) {//M
                return "M" + toElbonian();
            } else if (Integer.parseInt(number) > 300) {//D
                return "D" + toElbonian();
            } else if (Integer.parseInt(number) > 100) {//C
                return "C" + toElbonian();
            } else if (Integer.parseInt(number) > 30) {//L
                return "L" + toElbonian();
            } else if (Integer.parseInt(number) > 10) {//X
                return "X" + toElbonian();
            } else if (Integer.parseInt(number) > 3) {
                return "V" + toElbonian();
            } else if (Integer.parseInt(number) > 1) {
                return "V" + toElbonian();
            } else if (Integer.parseInt(number) == 0) {
                return "Z";
            }
            return null;
        }
        return number;
    }
    
    private void checkForErrorsElbonian() throws ValueOutOfBoundsException, MalformedNumberException {
        checkMagnitude();
        
        /*10 The range of numbers that can be represented by Elbonian numerals are the integers from -9999 to 9999.
        All Arabic integer inputs outside that range will result in a ValueOutOfBounds exception. All other input
        errors will result in MalformedNumber exceptions.
        if((Integer.parseInt(number)>9999) ||(Integer.parseInt(number)<-9999)){
            throw new ValueOutOfBoundsException("Number is either greater than 9999 or less than -9999");
        }*/
    }
    
    public boolean isInteger(String str){
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public void checkMagnitude() throws MalformedNumberException {
        if(!isInteger(number)) {
            char[] charArray = number.toCharArray();
        /*9 Numbers are represented by the letters from the greatest magnitude down to the least magnitude. In other
        words, the letter X would never appear before the letters N, M, D, C or L. The letter C would never appear
        before N, M or D. The letters are summed together to determine the value.*/
    
            String numberStr = "";
            // N = 3000 M = 1000 D = 300 C = 100 L = 30           X = 10      V = 3 I = 1 Z = 0
            for (char ch : charArray) {
                switch (ch) {
                    case 'N':
                        numberStr += 1;
                        break;
                    case 'M':
                        numberStr += 2;
                        break;
                    case 'D':
                        numberStr += 3;
                        break;
                    case 'C':
                        numberStr += 4;
                        break;
                    case 'L':
                        numberStr += 5;
                        break;
                    case 'X':
                        numberStr += 6;
                        break;
                    case 'V':
                        numberStr += 7;
                        break;
                    case 'I':
                        numberStr += 8;
                        break;
                    case 'Z':
                        numberStr += 9;
                        break;
                    default:
            
                }
            }
    
            if (!isMagnitudeCorrect(numberStr)) {
                throw new MalformedNumberException("The magnitude is not correct");
            }
        }else{
            throw new MalformedNumberException("Is Arabic, expected Elbonian");
        }
    }
    
    public void checkForErrorsArabic() throws MalformedNumberException, ValueOutOfBoundsException {
        checkMagnitude();
        checkForErrorsElbonian();
        //1. The following letters – M, C, X, and I – can each be repeated up to two times in a row. For example,
        char[] charArray = number.toCharArray();
        for(int i = 0; i < charArray.length - 2; i++){
            char temp = charArray[i];
            if(charArray[i] == 'M' || charArray[i] == 'C' || charArray[i] == 'X' || charArray[i] == 'I'){
                if(temp == charArray[i+1] && temp == charArray[i+2]){
                    throw new MalformedNumberException("Error! Cannot have more than 2 of M,C,X,I in a row!");
                }
            }
        }
        
        //2. The following letters – N, D, L, V – can each appear up to three times in a row.
        for(int i = 0; i < charArray.length-3; i++){
            char temp = charArray[i];
            if(temp == 'N' || temp == 'D' || temp == 'L' || temp == 'V'){
                if(temp == charArray[i+1] && temp == charArray[i+2] && temp == charArray[i+3]){
                    throw new MalformedNumberException("Error! Cannot have more than 3 of N,D,L,V in a row!");
                }
            }
        }
    
        //3. If N appears three times, then M cannot appear.
        int N_counter = 0;
        for(char ch : charArray){
            if(ch == 'N'){
                N_counter++;
            }
        }
        
        if(N_counter >= 3 && number.contains("M")){
            throw new MalformedNumberException("Error! Cannot have 3 N's and have an M appear");
        }
    
        //4. If D appears three times, then C cannot appear.
        checkForThreeDs();
    
        //5. If L appears three times, then X cannot appear.
        int L_counter = 0;
        for(char ch : charArray){
            if(ch == 'L'){
                L_counter++;
            }
        }
    
        if(L_counter >= 3 && number.contains("X")){
            throw new MalformedNumberException("Error! Cannot have 3 L's and have an X appear");
        }
    
        //6. If V appears three times, then I cannot appear.
        int V_counter = 0;
        for(char ch : charArray){
            if(ch == 'V'){
                V_counter++;
            }
        }
    
        if(V_counter >= 3 && number.contains("I")){
            throw new MalformedNumberException("Error! Cannot have 3 V's and have an I appear");
        }
        
        
        //8. Z can only appear once and without any other letters or minus sign. -0 is a malformed input.
        if((number.length()>1 && number.contains("Z"))){
            throw new MalformedNumberException("Z found");
        }
        
        /*11 Leading and trailing spaces will NOT result in an error, but spaces in the middle of the number will result in a
        MalformedNumber exception. Leading and trailing spaces should be trimmed.*/
       if(isSpaceInBetween()){
           throw new MalformedNumberException("Spaces found in between chars (11)");
       }
        
        /*12 Elbonian numbers are casesensitive. As a result, lowercase letters would result in malformed numbers.*/
        if((!isStringUpperCase(charArray)) && number.charAt(0)!='-'){
            throw new MalformedNumberException("Lowercase letters found (12)");
        }
    }
    
    private boolean isSpaceInBetween(){
        String[] str=number.trim().split(" ");
        return str.length > 1;
    }
    
    private static boolean isStringUpperCase(char[] charArray){
        for(int i=0; i < charArray.length; i++){
            //if any character is not in upper case, return false
            if( !Character.isUpperCase( charArray[i] ))
                return false;
        }
        
        return true;
    }
    //4. If D appears three times, then C cannot appear.
    private void checkForThreeDs() throws MalformedNumberException {
        char[] charArray = number.toCharArray();
        int D_counter = 0;
        for(char ch : charArray){
            if(ch == 'D'){
                D_counter++;
            }
        }
    
        if(D_counter >= 3 && number.contains("C")){
            throw new MalformedNumberException("Error! Cannot have 3 D's and have an C appear");
        }
    }
    
    /*9 Numbers are represented by the letters from the greatest magnitude down to the least magnitude. In other
        words, the letter X would never appear before the letters N, M, D, C or L. The letter C would never appear
        before N, M or D. The letters are summed together to determine the value.*/
    private boolean isMagnitudeCorrect(String number) {
            int first = Integer.parseInt(String.valueOf(number.charAt(0)));
            char[] lastPart = number.toCharArray();
    
            if (number.length() >= 2) {
        
                for (int i = 0; i < lastPart.length; i++) {
                    int next = Integer.parseInt(String.valueOf(lastPart[i]));
                    if (first > next) {
                        return false;
                    }
                }
                
                return true && isMagnitudeCorrect(String.valueOf(number.substring(1, number.length()).toCharArray()));
        
            } else if (number.isEmpty() || number.equals("Z") || number.length() == 1) {
                return true;
            }
    
            return true;
        }
}
