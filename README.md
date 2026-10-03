# Employee & Telephone Directory Management System

A Java-based console application designed to manage employee records, department classifications, and telephone directory assignments using lightweight, pipe-delimited text file persistence.

---

## 📌 Features

### 🏢 1. Department Code Maintenance
- **Auto-generated Department Codes**: Department codes automatically begin at `1000` and increment sequentially (`1000`, `1001`, etc.).
- **Validation**: Enforces maximum name length of 15 characters and prevents duplicate department names (case-insensitive).
- **View Departments**: Displays all registered departments in a structured tabular format.

### 👤 2. Employee Master Maintenance
- **Auto-generated Employee IDs**: Employee IDs start at `1000` and auto-increment with each new entry.
- **Department Verification**: Ensures assigned department codes exist in the system and auto-fills department metadata.
- **Validation**: Enforces name length limit (max 25 chars) and location tag limit (max 5 chars).
- **View Employees**: Displays employee records complete with ID, Name, Department Code, Department Name, and Location.

### 📞 3. Telephone Directory Maintenance
- **Algorithmic Telephone Number Generation**: Automatically generates numbers structured as `(DeptCode * 1000) + SequenceNumber` (e.g., `1000001`, `1000002`).
- **Employee Association**: Binds telephone numbers directly to verified employee profiles and departments.
- **View Directory**: Lists all directory entries formatted with telephone number, employee ID, employee name, department, and location.

### 🔍 4. Telephone Enquiry
- **Search by Employee Name**: Case-insensitive lookup returning linked phone records and full employee details.
- **Search by Telephone Number**: Direct phone number lookup showing associated employee details.

### 🔐 5. Setup & Authentication
- **First-Time Setup Flow**: Automatically triggers on initial launch if no employees exist, requiring initial department and employee registration before system access.
- **User Authentication**: Simple login using a valid Employee ID.

---

## 📂 Project Structure & File Schemas

```
Employee-Files-Management-/
├── Main.java         # Core application containing domain models & CLI menu logic
├── dept.txt          # Flat-file database for departments
├── emp.txt           # Flat-file database for employee records
├── tel.txt           # Flat-file database for telephone directory records
└── README.md         # Project documentation
```

### Data File Schemas (Pipe-Delimited `|`)

#### `dept.txt`
`DepartmentCode|DepartmentName`
```text
1000|Computer
1001|Mechanic
1002|Civil
```

#### `emp.txt`
`EmployeeID|Name|DeptCode|DeptName|Location`
```text
1000|Rahul|1000|Computer|BLR
1001|Anu|1001|Finance|MUM
```

#### `tel.txt`
`TelephoneNo|EmployeeID|DeptCode`
```text
1000001|1000|1000
1000002|1000|1000
1001001|1001|1001
```

---

## 🚀 Getting Started

### Prerequisites
- **Java Development Kit (JDK)**: Java 8 or higher.

### Compilation
Compile `Main.java` using `javac`:
```bash
javac Main.java
```

### Running the Application
Launch the program:
```bash
java Main
```

---

## 💻 System Usage Walkthrough

1. **Initial Launch**: If `emp.txt` is empty or missing records, the application launches into **First Time Setup**.
2. **Add Department & Employee**: Add at least one department and one employee to unlock system login.
3. **Login**: Enter a valid Employee ID (e.g., `1000`) to log in.
4. **Main Menu Navigation**:
   - `1`: Department Code Maintenance (Add / View Departments)
   - `2`: Employee Master Maintenance (Add / View Employees)
   - `3`: Telephone Directory Maintenance (Add / View Phone Numbers)
   - `4`: Telephone Enquiry (Search by Employee Name or Phone Number)
   - `5`: Exit application