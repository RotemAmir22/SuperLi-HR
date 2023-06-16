package Service_HR;

import java.time.LocalDate;
import java.util.Objects;

public abstract class AValidateInput {

    public boolean isString(Object o) {
        try{
            String tmp = (String) o;
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public  boolean isIdType(String input) {
        // Check if the string contains only numbers and has a length up to 10 characters
        if (input.matches("\\d{6,10}")) {
            return true; // String is valid
        } else {
            return false; // String is invalid
        }
    }

    public boolean isDouble(Object o){
        try{
            Double.parseDouble(o.toString());
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public boolean isInteger(Object o){
        try{
            Integer tmp = (Integer) o;
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public boolean isDate(Object o){
        try{
            LocalDate.parse(o.toString());
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean isPhone(String input){
        if (input.matches("\\d{3,10}")) {
            return true;
        } else {
            return false;
        }
    }

}
