import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

    // Add Mysql database Credentials
    public static final String username = "ADD USERNAME";
    public static final String password = "ADD PASSWORD";
    public static final String url = "ADD JDBC URL";

    // Color codes
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String BLUE = "\033[0;34m";
    public static final String RED_BG = "\033[41m";
    public static final String RESET = "\033[0m";

    //shared scanner
    public static final Scanner scan = new Scanner(System.in);

    //connection
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url,username,password);
        } catch (Exception e) {
            System.out.println(RED+"[!] Database Connection Failed \n"+ RESET);
        }
        return null;
    }

    // List students
    public static void viewStudent(){
        try( Connection con = getConnection()) {
            System.out.println(GREEN+"\n\n~~~~~~~~~~| VIEW ALL STUDENTS |~~~~~~~~~~\n\n"+RESET);

            String sql = "select * from Students;";

            Statement st = con.createStatement();
            ResultSet resultSet = st.executeQuery(sql);

            while (resultSet.next()){
                System.out.println("------------------------------------------");
                System.out.println("+ StudentID : " + resultSet.getString(1));
                System.out.println("+ FirstName : " + resultSet.getString(2));
                System.out.println("+ LastName : " + resultSet.getString(3));
                System.out.println("+ DateOfBirth : " + resultSet.getString(4));
                System.out.println("+ Email : " + resultSet.getString(5));
                System.out.println("+ Phone Number : " + resultSet.getString(6));
            }
            st.close();
            flowControl();
        } catch (Exception e) {
            System.out.println(RED+"[!] Error Occured" +RESET);
            System.out.println(RED+e.getMessage()+RESET);
        }
    }

    // Add student record
    public static void addStudent(){
        try( Connection con = getConnection()) {
            System.out.println(GREEN+"\n\n~~~~~~~~~~| ADD STUDENT PROCESS |~~~~~~~~~~\n\n"+RESET);

            String fName;
            while (true) {
                System.out.print("[#] Enter Student First Name :: ");
                fName = scan.next();
                if (fName.length() > 0 || fName.length() < 120) {
                    break;
                }
                System.out.println(RED + "[!] Invalid Input." + RESET);
            }
            String lName;
            while (true) {
                System.out.print("[#] Enter Student Last Name :: ");
                lName = scan.next();
                if (lName.length() > 0 || lName.length() < 120) {
                    break;
                }
                System.out.println(RED + "[!] Invalid Input." + RESET);
            }
            String dob;
            while (true) {
                System.out.print("[#] Enter Date Of Birth (YYYY-MM-DD):: ");
                dob = scan.next();
                if (dob.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
                    break;
                }
                System.out.println(RED + "[!] Invalid Date Format." + RESET);
            }
            String email;
            while (true) {
                System.out.print("[#] Enter Email :: ");
                email = scan.next();
                if (email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                    break;
                }
                System.out.println(RED + "[!] Invalid Email Format." + RESET);
            }
            String phoneNo;
            while (true) {
                System.out.print("[#] Enter Phone No. (10digit):: ");
                phoneNo = scan.next();
                if (phoneNo.length() == 10) {
                    break;
                }
                System.out.println(RED + "[!] Phone Number must be exactly 10 digits." + RESET);
            }

            String sql = "INSERT INTO Students(FirstName, LastName, DateOfBirth, Email, PhoneNumber) " + "VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement st = con.prepareStatement(sql)) {
                st.setString(1, fName);
                st.setString(2, lName);
                st.setDate(3, java.sql.Date.valueOf(dob));
                st.setString(4, email);
                st.setString(5, phoneNo);

                st.executeUpdate();
                System.out.println(BLUE+"\n\n# SUCCESSFULLY ADDED NEW STUDENT RECORD"+RESET);

                st.close();
            }

            con.close();
            flowControl();
        } catch (Exception e) {
            System.out.println(RED+"[!] Error Occured" +RESET);
            System.out.println(RED+e.getMessage()+RESET);
        }
    }

    // update student records
    public static void update(){
        System.out.println(GREEN+"\n\n~~~~~~~~~~| UPDATE STUDENT RECORDS |~~~~~~~~~~\n\n"+RESET);

        String sql;

        System.out.print("[#] Enter Student ID : ");
        int stuId = scan.nextInt();

        System.out.println("\n\n# STUDENT RECORD");
        boolean RecordAvailable = specificView(stuId);

        if (RecordAvailable){
            try( Connection con = getConnection()) {
                int updateItem;
                sql="";
                while (true){
                    System.out.print("\n[#] Enter ItemNo. of the item you want to update (1-6): ");
                    updateItem = scan.nextInt();
                    if (updateItem>0 || updateItem<=6){
                        break;
                    }
                }
                switch (updateItem){
                    case 1:
                        System.out.println(RED+"[!] StudentID can not be updated manually."+RESET);
                        return;
                    case 2:
                        String fName;
                        while (true) {
                            System.out.print("\n[#] Enter Student First Name :: ");
                            fName = scan.next();
                            if (fName.length() > 0 || fName.length() < 120) {
                                break;
                            }
                            System.out.println(RED + "[!] Invalid Input." + RESET);
                        }

                        sql = "Update Students set FirstName='"+fName+"' where StudentID = "+stuId+";";
                        break;
                    case 3:
                        String lName;
                        while (true) {
                            System.out.print("\n[#] Enter Student Last Name :: ");
                            lName = scan.next();
                            if (lName.length() > 0 || lName.length() < 120) {
                                break;
                            }
                            System.out.println(RED + "[!] Invalid Input." + RESET);
                        }
                        sql = "Update Students set LastName='"+lName+"'  where StudentID = "+stuId+";";
                        break;
                    case 4:
                        String dob;
                        while (true) {
                            System.out.print("\n[#] Enter Date Of Birth (YYYY-MM-DD):: ");
                            dob = scan.next();
                            if (dob.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
                                break;
                            }
                            System.out.println(RED + "[!] Invalid Date Format." + RESET);
                        }
                        sql = "Update Students set DateOfBirth='"+dob+"' where StudentID = "+stuId+";";
                        break;
                    case 5:
                        String email;
                        while (true) {
                            System.out.print("\n[#] Enter Email :: ");
                            email = scan.next();
                            if (email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                                break;
                            }
                            System.out.println(RED + "[!] Invalid Email Format." + RESET);
                        }
                        sql = "Update Students set Email='"+email+"' where StudentID = "+stuId+";";
                        break;
                    case 6:
                        String phoneNo;
                        while (true) {
                            System.out.print("\n[#] Enter Phone No. (10digit):: ");
                            phoneNo = scan.next();
                            if (phoneNo.length() == 10) {
                                break;
                            }
                            System.out.println(RED + "[!] Phone Number must be exactly 10 digits." + RESET);
                        }
                        sql = "Update Students set PhoneNumber='"+phoneNo+"' where StudentID = "+stuId+";";
                        break;
                    default:
                        System.out.println(RED+"[!] Invalid Input."+RESET);
                }

                //Update process
                Statement st = con.createStatement();
                st.executeUpdate(sql);
                System.out.println(BLUE+"\n[*] Record Updated Successfully.\n"+RESET);

                //list down updated record
                System.out.println("\n# UPDATED STUDENT RECORD");
                specificView(stuId);

                st.close();
                flowControl();
            } catch (Exception e) {
                System.out.println(RED+"[!] Error Occured" +RESET);
                System.out.println(RED+e.getMessage()+RESET);
            }
        }
    }

    // Search or fetch specified student record
    static boolean specificView(int stuId) {
        try (Connection con = getConnection()) {
            String sql = "select * from Students where StudentID="+ stuId + ";";
            Statement st = con.createStatement();
            ResultSet resultSet = st.executeQuery(sql);

            if (!resultSet.next()){
                System.out.println(RED+"\n[!] Student Record Not Found"+RESET);

            }else {

                do{
                    System.out.println("------------------------------------------");
                    System.out.println("1- StudentID : " + resultSet.getString(1));
                    System.out.println("2- FirstName : " + resultSet.getString(2));
                    System.out.println("3- LastName : " + resultSet.getString(3));
                    System.out.println("4- DateOfBirth : " + resultSet.getString(4));
                    System.out.println("5- Email : " + resultSet.getString(5));
                    System.out.println("6- Phone Number : " + resultSet.getString(6));
                }while (resultSet.next());
                return true;
            }
            st.close();
            flowControl();
        }catch (SQLException e ){
            System.out.println(RED+"[!] Error Occured" +RESET);
            System.out.println(RED+e.getMessage()+RESET);
        }
        return false;
    }

    // delete student record
    static void delete(){
        System.out.println(GREEN+"\n\n~~~~~~~~~~| DELETE STUDENT RECORDS |~~~~~~~~~~\n\n"+RESET);

        System.out.print("[#] Enter Student ID : ");
        int stuId = scan.nextInt();

        try( Connection con = getConnection()) {
            String sql = "DELETE FROM Students WHERE StudentID = "+stuId+";";

            //delete process
            Statement st = con.createStatement();
            int rowsAffected = st.executeUpdate(sql);
            if (rowsAffected>0){
                System.out.println(GREEN+"\n[*] Record Deleted Successfully.\n"+RESET);
            }else{
                System.out.println(RED+"\n[!] Student Record Not Found.\n"+RESET);
            }
            st.close();
            flowControl();
        } catch (Exception e) {
            System.out.println(RED+"[!] Error Occured" +RESET);
            System.out.println(RED+e.getMessage()+RESET);
        }

    }

    //System flow controller
    static void flowControl(){

        System.out.print(RED + "\n\n[#] Press Q to quit, else continue : " + RESET);
        String Choice = scan.next().trim();

        if (Choice.equalsIgnoreCase("Q")){
            System.out.println(RED+"[*] Thank You ..."+RESET);
            System.exit(0);
        }
    }


    public static void main(String[] args) {
        while(true){
            System.out.println(GREEN+"\n\n#******** STUDENT MANAGEMENT SYSTEM ********#"+RESET);
            System.out.println("\t"+BLUE+RED_BG +" [+] Coded By Shehan Sulakshana [+] "+RESET);

            System.out.println("\n\t[1]. Add Student");
            System.out.println("\t[2]. View All Students");
            System.out.println("\t[3]. Update Student");
            System.out.println("\t[4]. Delete Student");
            System.out.println("\t[5]. Search Student");
            System.out.println("\t[6]. Exit");

            System.out.print("\n[#] Select An Option :: ");
            int option = scan.nextInt();

            switch (option){
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewStudent();
                    break;
                case 3:
                    update();
                    break;
                case 4:
                    delete();
                    break;
                case 5:
                    System.out.println(GREEN+"\n\n~~~~~~~~~~| SEARCH STUDENT RECORDS |~~~~~~~~~~\n\n"+RESET);
                    System.out.print("[#] Enter Student ID : ");
                    int stuId = scan.nextInt();
                    specificView(stuId);
                    break;
                case 6:
                    System.out.println(RED+"[*] Thank You ..."+RESET);
                    System.exit(0);
                    break;
                default:
                    System.out.println(RED+"[!] Invalid Option"+RESET);
                    System.exit(0);
            }
        }
    }
}