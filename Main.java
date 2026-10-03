
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/* Structure Classes */
class Department {

    int code;
    String name;
}

class Employee {

    int id;
    String name;
    int deptCode;
    String deptName;
    String location;
}

class Telephone {

    long telNo;
    int empId;
    int deptCode;
}

public class Main {

    static Scanner sc = new Scanner(System.in);

    /* Helper: Ensures dept.txt, emp.txt, and tel.txt exist on disk */
    public static void ensureFilesExist() {
        try {
            new File("dept.txt").createNewFile();
            new File("emp.txt").createNewFile();
            new File("tel.txt").createNewFile();
        } catch (IOException e) {

        }
    }

    /* Department Maintenance Module */
    public static int getNextDepartmentCode() {
        File file = new File("dept.txt");
        if (!file.exists()) {
            return 1000;
        }

        int maxCode = 999;
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length >= 2) {
                    int code = Integer.parseInt(parts[0]);
                    if (code > maxCode) {
                        maxCode = code;
                    }
                }
            }
        } catch (Exception e) {
        }
        return maxCode + 1;
    }

    public static boolean isDepartmentNameDuplicate(String name) {
        File file = new File("dept.txt");
        if (!file.exists()) {
            return false;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length >= 2) {
                    if (parts[1].equalsIgnoreCase(name)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static Department getDepartmentByCode(int code) {
        File file = new File("dept.txt");
        if (!file.exists()) {
            return null;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length >= 2) {
                    int c = Integer.parseInt(parts[0]);
                    if (c == code) {
                        Department dept = new Department();
                        dept.code = c;
                        dept.name = parts[1];
                        return dept;
                    }
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static void addDepartment() {
        int code = getNextDepartmentCode();
        String name = "";

        System.out.println("\n--- ADD DEPARTMENT ---");
        System.out.println("Generated Department Code: " + code);

        while (true) {
            System.out.print("Enter Department Name (Max 15 chars): ");
            name = sc.nextLine().trim();
            if (name.isEmpty()) {
                continue;
            }
            if (name.length() > 15) {
                System.out.println("Error: Department name cannot exceed 15 characters.");
                continue;
            }
            if (isDepartmentNameDuplicate(name)) {
                System.out.println("Error: Department name already exists (case-insensitive check).");
                continue;
            }
            break;
        }

        try (FileWriter fw = new FileWriter("dept.txt", true); PrintWriter pw = new PrintWriter(fw)) {
            pw.println(code + "|" + name);
        } catch (IOException e) {
            System.out.println("Error: Could not open dept.txt for writing.");
            return;
        }

        System.out.println("Department added successfully! [Code: " + code + ", Name: " + name + "]");
    }

    public static void viewDepartments() {
        File file = new File("dept.txt");
        if (!file.exists()) {
            System.out.println("Error: Could not open dept.txt");
            return;
        }

        int count = 0;
        System.out.println("\n============================================");
        System.out.println("           ALL DEPARTMENTS                  ");
        System.out.println("============================================\n");
        System.out.printf("%-15s %-25s\n", "Dept Code", "Department Name");
        System.out.println("--------------------------------------------");

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length >= 2) {
                    int code = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    System.out.printf("%-15d %-25s\n", code, name);
                    count++;
                }
            }
        } catch (Exception e) {
        }

        if (count == 0) {
            System.out.println("No department records found.");
        }
        System.out.println("--------------------------------------------");
    }

    public static void departmentMaintenanceMenu() {
        int choice;
        do {
            System.out.println("\n============================================");
            System.out.println("       DEPARTMENT CODE MAINTENANCE          ");
            System.out.println("============================================");
            System.out.println("1. Add Department");
            System.out.println("2. View All Departments");
            System.out.println("3. Back");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addDepartment();
                    break;
                case 2:
                    viewDepartments();
                    break;
                case 3:
                    System.out.println("Returning to previous menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid menu option.");
            }
        } while (choice != 3);
    }

    /* Employee Master Maintenance Module */
    public static int getNextEmployeeId() {
        File file = new File("emp.txt");
        if (!file.exists()) {
            return 1000;
        }

        int maxId = 999;
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length >= 5) {
                    int id = Integer.parseInt(parts[0]);
                    if (id > maxId) {
                        maxId = id;
                    }
                }
            }
        } catch (Exception e) {
        }
        return maxId + 1;
    }

    public static Employee getEmployeeById(int id) {
        File file = new File("emp.txt");
        if (!file.exists()) {
            return null;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length >= 5) {
                    int empId = Integer.parseInt(parts[0]);
                    if (empId == id) {
                        Employee emp = new Employee();
                        emp.id = empId;
                        emp.name = parts[1];
                        emp.deptCode = Integer.parseInt(parts[2]);
                        emp.deptName = parts[3];
                        emp.location = parts[4];
                        return emp;
                    }
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static int getEmployeeCount() {
        File file = new File("emp.txt");
        if (!file.exists()) {
            return 0;
        }

        int count = 0;
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length >= 5) {
                    count++;
                }
            }
        } catch (Exception e) {
        }
        return count;
    }

    public static void addEmployee() {
        int empId = getNextEmployeeId();
        String empName = "";
        String location = "";
        int deptCode;
        Department dept;

        System.out.println("\n--- ADD EMPLOYEE ---");

        /*  Employee Name */
        while (true) {
            System.out.print("Enter Employee Name (Max 25 chars): ");
            empName = sc.nextLine().trim();
            if (empName.isEmpty()) {
                continue;
            }
            if (empName.length() > 25) {
                System.out.println("Error: Employee Name cannot exceed 25 characters.");
                continue;
            }
            break;
        }

        /* Show Available Departments & Select Department Code */
        System.out.println("\nAvailable Departments:");
        viewDepartments();

        while (true) {
            System.out.print("Enter Department Code: ");
            deptCode = sc.nextInt();
            sc.nextLine();
            dept = getDepartmentByCode(deptCode);
            if (dept == null) {
                System.out.println("Error: Department code " + deptCode + " does not exist in dept.txt! Try again.");
                continue;
            }
            break;
        }

        /* Automatically display Department Name */
        System.out.println("Department Name: " + dept.name);

        /*  Location */
        while (true) {
            System.out.print("Enter Location (Max 5 chars): ");
            location = sc.nextLine().trim();
            if (location.isEmpty()) {
                continue;
            }
            if (location.length() > 5) {
                System.out.println("Error: Location cannot exceed 5 characters.");
                continue;
            }
            break;
        }

        /* Save record to emp.txt */
        try (FileWriter fw = new FileWriter("emp.txt", true); PrintWriter pw = new PrintWriter(fw)) {
            pw.println(empId + "|" + empName + "|" + deptCode + "|" + dept.name + "|" + location);
        } catch (IOException e) {
            System.out.println("Error: Could not open emp.txt for writing.");
            return;
        }

        System.out.println("\nEmployee saved successfully!");
        System.out.println("Assigned Employee ID: " + empId);
    }

    public static void viewEmployees() {
        File file = new File("emp.txt");
        if (!file.exists()) {
            System.out.println("Error: Could not open emp.txt");
            return;
        }

        int count = 0;
        System.out.println("\n==============================================================");
        System.out.println("                     ALL EMPLOYEES                            ");
        System.out.println("==============================================================\n");
        System.out.printf("%-8s %-22s %-12s %-15s %-8s\n", "ID", "Name", "Dept Code", "Dept Name", "Location");
        System.out.println("--------------------------------------------------------------");

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length >= 5) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    int deptCode = Integer.parseInt(parts[2]);
                    String deptName = parts[3];
                    String location = parts[4];
                    System.out.printf("%-8d %-22s %-12d %-15s %-8s\n", id, name, deptCode, deptName, location);
                    count++;
                }
            }
        } catch (Exception e) {
        }

        if (count == 0) {
            System.out.println("No employee records found.");
        }
        System.out.println("--------------------------------------------------------------");
    }

    public static void employeeMaintenanceMenu() {
        int choice;
        do {
            System.out.println("\n============================================");
            System.out.println("       EMPLOYEE MASTER MAINTENANCE          ");
            System.out.println("============================================");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Back");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    viewEmployees();
                    break;
                case 3:
                    System.out.println("Returning to previous menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid menu option.");
            }
        } while (choice != 3);
    }

    /* Telephone Directory Maintenance Module */
    public static long generateTelephoneNumber(int deptCode) {
        File file = new File("tel.txt");
        long maxSeq = 0;

        if (file.exists()) {
            try (Scanner fileScanner = new Scanner(file)) {
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine().trim();
                    if (line.isEmpty()) {
                        continue;
                    }
                    String[] parts = line.split("\\|");
                    if (parts.length >= 3) {
                        long telNo = Long.parseLong(parts[0]);
                        int code = Integer.parseInt(parts[2]);
                        if (code == deptCode) {
                            long seq = telNo % 1000;
                            if (seq > maxSeq) {
                                maxSeq = seq;
                            }
                        }
                    }
                }
            } catch (Exception e) {
            }
        }

        long nextSeq = maxSeq + 1;
        return (long) deptCode * 1000 + nextSeq;
    }

    public static void addTelephoneNumber() {
        System.out.println("\n--- ADD TELEPHONE NUMBER ---");
        System.out.print("Enter Employee ID: ");
        int empId = sc.nextInt();
        sc.nextLine();

        Employee emp = getEmployeeById(empId);
        if (emp == null) {
            System.out.println("Error: Employee ID " + empId + " does not exist in emp.txt!");
            return;
        }

        /* Automatically display employee details */
        System.out.println("\nEmployee Name : " + emp.name);
        System.out.println("Department    : " + emp.deptCode + " - " + emp.deptName);
        System.out.println("Location      : " + emp.location);

        /* Generate telephone number automatically */
        long telNo = generateTelephoneNumber(emp.deptCode);
        System.out.println("Generated Telephone Number: " + telNo);

        /* Save to tel.txt */
        try (FileWriter fw = new FileWriter("tel.txt", true); PrintWriter pw = new PrintWriter(fw)) {
            pw.println(telNo + "|" + emp.id + "|" + emp.deptCode);
        } catch (IOException e) {
            System.out.println("Error: Could not open tel.txt for writing.");
            return;
        }

        System.out.println("\nTelephone number record added successfully!");
    }

    public static void viewTelephoneDirectory() {
        File file = new File("tel.txt");
        if (!file.exists()) {
            System.out.println("Error: Could not open tel.txt");
            return;
        }

        int count = 0;
        System.out.println("\n================================================================================");
        System.out.println("                          TELEPHONE DIRECTORY                                   ");
        System.out.println("================================================================================\n");
        System.out.printf("%-12s %-8s %-20s %-20s %-8s\n", "Telephone", "Emp ID", "Employee Name", "Department", "Location");
        System.out.println("--------------------------------------------------------------------------------");

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length >= 3) {
                    long telNo = Long.parseLong(parts[0]);
                    int empId = Integer.parseInt(parts[1]);
                    int deptCode = Integer.parseInt(parts[2]);

                    Employee emp = getEmployeeById(empId);
                    if (emp != null) {
                        String deptDisplay = emp.deptCode + " - " + emp.deptName;
                        System.out.printf("%-12d %-8d %-20s %-20s %-8s\n", telNo, emp.id, emp.name, deptDisplay, emp.location);
                    } else {
                        System.out.printf("%-12d %-8d %-20s %-20s %-8s\n", telNo, empId, "Unknown", "N/A", "N/A");
                    }
                    count++;
                }
            }
        } catch (Exception e) {
        }

        if (count == 0) {
            System.out.println("No telephone records found.");
        }
        System.out.println("--------------------------------------------------------------------------------");
    }

    public static void telephoneMaintenanceMenu() {
        int choice;
        do {
            System.out.println("\n============================================");
            System.out.println("     TELEPHONE DIRECTORY MAINTENANCE        ");
            System.out.println("============================================");
            System.out.println("1. Add Telephone Number");
            System.out.println("2. View Telephone Directory");
            System.out.println("3. Back");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addTelephoneNumber();
                    break;
                case 2:
                    viewTelephoneDirectory();
                    break;
                case 3:
                    System.out.println("Returning to previous menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid menu option.");
            }
        } while (choice != 3);
    }

    /* Telephone Enquiry Module */
    public static void enquiryByEmployeeName() {
        System.out.println("\n--- ENQUIRY BY EMPLOYEE NAME ---");
        System.out.print("Enter Employee Name: ");
        String targetName = sc.nextLine().trim();

        File fileEmp = new File("emp.txt");
        if (!fileEmp.exists()) {
            System.out.println("Error: Could not open emp.txt");
            return;
        }

        boolean employeeFound = false;
        int phoneRecordsFound = 0;

        try (Scanner fileScanner = new Scanner(fileEmp)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length >= 5) {
                    String empName = parts[1];
                    if (empName.equalsIgnoreCase(targetName)) {
                        employeeFound = true;
                        int empId = Integer.parseInt(parts[0]);
                        int deptCode = Integer.parseInt(parts[2]);
                        String deptName = parts[3];
                        String location = parts[4];

                        /* Look up telephone numbers for this employee */
                        File fileTel = new File("tel.txt");
                        if (fileTel.exists()) {
                            try (Scanner fileTelScanner = new Scanner(fileTel)) {
                                while (fileTelScanner.hasNextLine()) {
                                    String telLine = fileTelScanner.nextLine().trim();
                                    if (telLine.isEmpty()) {
                                        continue;
                                    }
                                    String[] telParts = telLine.split("\\|");
                                    if (telParts.length >= 3) {
                                        int tEmpId = Integer.parseInt(telParts[1]);
                                        if (tEmpId == empId) {
                                            phoneRecordsFound++;
                                            long telNo = Long.parseLong(telParts[0]);
                                            System.out.println("--------------------------------------------");
                                            System.out.println("Telephone   : " + telNo);
                                            System.out.println("Employee ID : " + empId);
                                            System.out.println("Employee    : " + empName);
                                            System.out.println("Department  : " + deptCode + " - " + deptName);
                                            System.out.println("Location    : " + location);
                                            System.out.println("--------------------------------------------");
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
        }

        if (!employeeFound) {
            System.out.println("Employee name does not exist.");
        } else if (phoneRecordsFound == 0) {
            System.out.println("Employee exists, but no telephone numbers are assigned.");
        }
    }

    public static void enquiryByTelephoneNumber() {
        System.out.println("\n--- ENQUIRY BY TELEPHONE NUMBER ---");
        System.out.print("Enter Telephone Number: ");
        long targetTelNo = sc.nextLong();
        sc.nextLine();

        File fileTel = new File("tel.txt");
        if (!fileTel.exists()) {
            System.out.println("Error: Could not open tel.txt");
            return;
        }

        boolean found = false;

        try (Scanner fileScanner = new Scanner(fileTel)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts.length >= 3) {
                    long telNo = Long.parseLong(parts[0]);
                    if (telNo == targetTelNo) {
                        found = true;
                        int empId = Integer.parseInt(parts[1]);
                        int deptCode = Integer.parseInt(parts[2]);

                        System.out.println("--------------------------------------------");
                        System.out.println("Telephone   : " + telNo);
                        System.out.println("Employee ID : " + empId);

                        Employee emp = getEmployeeById(empId);
                        if (emp != null) {
                            System.out.println("Employee    : " + emp.name);
                            System.out.println("Department  : " + emp.deptCode + " - " + emp.deptName);
                            System.out.println("Location    : " + emp.location);
                        } else {
                            System.out.println("Employee    : Unknown");
                            System.out.println("Department  : " + deptCode);
                            System.out.println("Location    : N/A");
                        }
                        System.out.println("--------------------------------------------");
                        break;
                    }
                }
            }
        } catch (Exception e) {
        }

        if (!found) {
            System.out.println("Telephone number does not exist.");
        }
    }

    public static void telephoneEnquiryMenu() {
        int choice;
        do {
            System.out.println("\n============================================");
            System.out.println("             TELEPHONE ENQUIRY              ");
            System.out.println("============================================");
            System.out.println("1. Enquiry by Employee Name");
            System.out.println("2. Enquiry by Telephone Number");
            System.out.println("3. Back");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    enquiryByEmployeeName();
                    break;
                case 2:
                    enquiryByTelephoneNumber();
                    break;
                case 3:
                    System.out.println("Returning to previous menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid menu option.");
            }
        } while (choice != 3);
    }

    /* First-Time Setup & Login Flows */
    public static void firstTimeSetup() {
        int choice;
        do {
            System.out.println("\n========================================");
            System.out.println("          FIRST TIME SETUP              ");
            System.out.println("========================================");
            if (getEmployeeCount() == 0) {
                System.out.println("No employees exist yet.\n");
            } else {
                System.out.println("Employees registered: " + getEmployeeCount() + "\n");
            }
            System.out.println("1. Department Maintenance");
            System.out.println("2. Employee Maintenance");
            System.out.println("3. Continue to Login");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    departmentMaintenanceMenu();
                    break;
                case 2:
                    employeeMaintenanceMenu();
                    break;
                case 3:
                    if (getEmployeeCount() == 0) {
                        System.out.println("Error: At least one employee must exist before continuing to login.");
                    } else {
                        System.out.println("Setup complete! Proceeding to login...");
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1, 2, or 3.");
            }
        } while (choice != 3 || getEmployeeCount() == 0);
    }

    public static boolean performLogin() {
        System.out.println("\n========================================");
        System.out.println("      TELEPHONE DIRECTORY SYSTEM        ");
        System.out.println("========================================");
        System.out.println();

        System.out.print("Enter User ID: ");
        int userId = sc.nextInt();
        sc.nextLine();

        Employee emp = getEmployeeById(userId);
        if (emp != null) {
            System.out.println("\nLogin Successful! Welcome, " + emp.name + ".");
            return true;
        } else {
            System.out.println("\nLogin Denied");
            return false;
        }
    }

    public static void mainMenu() {
        int choice;
        do {
            System.out.println("\n========================================");
            System.out.println("              MAIN MENU                 ");
            System.out.println("========================================");
            System.out.println("1. Department Code Maintenance");
            System.out.println("2. Employee Master Maintenance");
            System.out.println("3. Telephone Directory Maintenance");
            System.out.println("4. Telephone Enquiry");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    departmentMaintenanceMenu();
                    break;
                case 2:
                    employeeMaintenanceMenu();
                    break;
                case 3:
                    telephoneMaintenanceMenu();
                    break;
                case 4:
                    telephoneEnquiryMenu();
                    break;
                case 5:
                    System.out.println("Exiting application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid menu option.");
            }
        } while (choice != 5);
    }

    /* Application Entry Point */
    public static void main(String[] args) {
        ensureFilesExist();
        if (getEmployeeCount() == 0) {
            firstTimeSetup();
        }
        if (!performLogin()) {

            return;
        }
        mainMenu();
    }
}
