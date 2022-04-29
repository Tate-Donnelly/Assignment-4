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
        this.number = number.trim();
    }

    /**
     * Converts the number to an Arabic numeral or returns the current value as an int if it is already
     * in the Arabic form.
     *
     * @return An arabic value
     */
    public int toArabic() throws MalformedNumberException, ValueOutOfBoundsException  {
        checkForErrorsArabic();
        boolean isNegative = false;
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
                case '-':
                    isNegative = true;
                    break;

            }
        }
        if(isNegative){
            total = -total;
        }

        return total;
    }
    /**
     * Converts the number to an Elbonian numeral or returns the current value if it is already in the Elbonian form.
     *
     * @return An Elbonian value
     */
    public String toElbonian() throws MalformedNumberException, ValueOutOfBoundsException {
        checkForErrorsElbonian();
        int ebloNum = Integer.parseInt(number);
        String elboStr = createElbonian(ebloNum);
        return elboStr;
    }

    private String createElbonian(int elboNum) throws MalformedNumberException, ValueOutOfBoundsException {
            if (elboNum < 0) {
                return "-" + createElbonian(Math.abs(elboNum));
            } else if (elboNum >= 3000) {//N
                return "N" + createElbonian(elboNum - 3000);
            } else if (elboNum >= 1000) {//M
                return "M" + createElbonian(elboNum - 1000);
            } else if (elboNum >= 300) {//D
                return "D" + createElbonian(elboNum - 300);
            } else if (elboNum >= 100) {//C
                return "C" + createElbonian(elboNum - 100);
            } else if (elboNum >= 30) {//L
                return "L" + createElbonian(elboNum - 30);
            } else if (elboNum >= 10) {//X
                return "X" + createElbonian(elboNum - 10);
            } else if (elboNum >= 3) {
                return "V" + createElbonian(elboNum - 3);
            } else if (elboNum >= 2) {
                return "I" + createElbonian(elboNum - 1);
            }else if (elboNum == 1) {
                return "I";
            }else if(Integer.parseInt(number) == elboNum){
                return "Z";
        }
            return "";
    }

    
    private void checkForErrorsElbonian() throws ValueOutOfBoundsException, MalformedNumberException {
        //TODO Are these the right exceptions?
        if(number.isEmpty()) {
            throw new MalformedNumberException("Empty String");
        }
    
        if(number.equals("-0")) {
            throw new MalformedNumberException("-0");
        }
        
        try{
            Integer.parseInt(number);
        }catch (NumberFormatException e){
            throw new MalformedNumberException("String is not a number!");
        }

        if(isSpaceInBetween()) {
            throw new MalformedNumberException("Spaces found in between chars (11)");
        }else{
            int elboNum = Integer.parseInt(number);
            if (elboNum > 9999 || elboNum < -9999) {
                throw new ValueOutOfBoundsException("Number must be between -9999 and 9999!");
            }
        }
        multipleMinusSigns();
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
    }
    
    public void checkForErrorsArabic() throws MalformedNumberException, ValueOutOfBoundsException {
        if(number.isEmpty()) {
            throw new MalformedNumberException("Empty String");
        }
        if(number.equals("-0")) {
            throw new MalformedNumberException("-0");
        }
        if(isInteger(number)){
            if(Integer.parseInt(number)>=9999 || Integer.parseInt(number)<=-9999) {
                throw new ValueOutOfBoundsException("Out of bounds");
            }
        }
        
        checkMagnitude();
        multipleMinusSigns();
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
        checkForMultiples(3, 'N',"M");
    
        //4. If D appears three times, then C cannot appear.
        checkForMultiples(3, 'D',"C");
    
        //5. If L appears three times, then X cannot appear.
        checkForMultiples(3, 'L',"X");
    
        //6. If V appears three times, then I cannot appear.
        checkForMultiples(3, 'V',"I");
        
        
        //8. Z can only appear once and without any other letters or minus sign. -0 is a malformed input.
        if((number.length()>1) && (number.contains("Z"))){
            throw new MalformedNumberException("Z found");
        }
        
        /*11 Leading and trailing spaces will NOT result in an error, but spaces in the middle of the number will result in a
        MalformedNumber exception. Leading and trailing spaces should be trimmed.*/
       if(isSpaceInBetween()){
           throw new MalformedNumberException("Spaces found in between chars (11)");
       }
        
        /*12 Elbonian numbers are casesensitive. As a result, lowercase letters would result in malformed numbers.*/
        if((!isStringUpperCase(charArray)) && number.charAt(0) != '-'){
            throw new MalformedNumberException("Lowercase letters found (12)");
        }
    }
    
    private void multipleMinusSigns() throws MalformedNumberException {
        char[] charArray = number.toCharArray();
        int counter = 0;
        for(char ch : charArray){
            if(ch == '-'){
                counter++;
            }
        }
        if(counter>1){
            throw new MalformedNumberException(counter+ " minus signs found");
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
    private void checkForMultiples(int i, char appearsMultipleTimes, String cannotAppear) throws MalformedNumberException {
        char[] charArray = number.toCharArray();
        int counter = 0;
        for(char ch : charArray){
            if(ch == appearsMultipleTimes){
                counter++;
            }
        }
    
        if(counter >= i && number.contains(cannotAppear)){
            throw new MalformedNumberException("Error! Cannot have " + i + " " + appearsMultipleTimes + "'s and have an " + cannotAppear + " appear");
        }
    }
    
    /*9 Numbers are represented by the letters from the greatest magnitude down to the least magnitude. In other
        words, the letter X would never appear before the letters N, M, D, C or L. The letter C would never appear
        before N, M or D. The letters are summed together to determine the value.*/
    private boolean isMagnitudeCorrect(String number) {
        if (number.length() > 1) {
            int first = Integer.parseInt(String.valueOf(number.charAt(0)));
            char[] lastPart = number.toCharArray();
            
            for (int i = 0; i < lastPart.length; i++) {
                int next = Integer.parseInt(String.valueOf(lastPart[i]));
                if (first > next) {
                    return false;
                }
            }
    
            return isMagnitudeCorrect(String.valueOf(number.substring(1).toCharArray()));
            
        }else {
            return true;
        }
    }
}
