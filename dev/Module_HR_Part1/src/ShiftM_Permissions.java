package Module_HR_Part1.src;

public class ShiftM_Permissions {

    private String name;
    private String description;

    public ShiftM_Permissions(String name, String description) {
        this.name = name;
        this.description = description;
    }
    //getters
    public String getName() {return name;}
    public String getDescription() {return description;}

    //there are no setter, you can delete the permission and create a new one that is more specific
}
