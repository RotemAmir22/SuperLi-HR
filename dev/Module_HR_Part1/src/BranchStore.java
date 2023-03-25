import java.util.List;

public class BranchStore {

    private static int serialNumCounter=0;
    private int branchID;
    private String name;
    private String address;
    private String phoneNum;
    private List<Employee> employees;
    private int[][] openHours;
    private DailyShift[][] shiftsHistory; // save 4 weeks back

}
