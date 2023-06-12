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

}
