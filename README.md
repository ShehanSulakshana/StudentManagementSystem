# 🎓 Student Management System

A Java console-based Student Management System connected to a MySQL database. This system allows users to add, view, update, search, and delete student records from the database using JDBC. Ideal for educational institutions or academic projects.

---

## 📦 Features

* Add new student records
* View all students
* Update existing student details
* Delete a student by ID
* Search for a student by ID
* Input validation (email, date format, phone number)
* Colored console output for better readability

---

## 🛠️ Technologies Used

* Java (JDK 8+)
* MySQL
* JDBC (Java Database Connectivity)
* IntelliJ IDEA or any Java IDE
* Git & GitHub

---

## 📚 Required Libraries

No external libraries are required apart from the standard **MySQL JDBC Driver**.

If you're using IntelliJ IDEA:

1. Go to **File > Project Structure > Libraries**
2. Add the MySQL connector JAR (e.g., `mysql-connector-java-8.x.x.jar`)

Or if you're compiling manually, place the JAR in your project directory and use:

```bash
javac -cp .;mysql-connector-java-8.x.x.jar Main.java
java -cp .;mysql-connector-java-8.x.x.jar Main
```

(Use `:` instead of `;` for Linux/macOS.)

---

## ⚙️ Setup Instructions

### 1. 📁 Create MySQL Database & Table

```sql
CREATE DATABASE StudentDB;
USE StudentDB;

CREATE TABLE Students (
    StudentID INT AUTO_INCREMENT PRIMARY KEY,
    FirstName VARCHAR(100),
    LastName VARCHAR(100),
    DateOfBirth DATE,
    Email VARCHAR(150),
    PhoneNumber VARCHAR(15)
);
```

---

### 2. 🔐 Set Your Database Credentials

Open `Main.java` and replace the placeholders with your actual database credentials:

```java
public static final String username = "YOUR_USERNAME";
public static final String password = "YOUR_PASSWORD";
public static final String url = "jdbc:mysql://localhost:3306/StudentDB";
```

✅ **Important:** Don’t commit your real credentials to GitHub!

---

### 3. 🚀 Compile and Run the Program

Using terminal:

```bash
javac Main.java
java Main
```

Or simply run `Main.java` from IntelliJ IDEA.

---

## 💡 Sample Menu

```
#******** STUDENT MANAGEMENT SYSTEM ********#
 [+] Coded By Shehan Sulakshana [+]

 [1]. Add Student
 [2]. View All Students
 [3]. Update Student
 [4]. Delete Student
 [5]. Search Student
 [6]. Exit
```

---

## 📌 Notes

* MySQL server must be running before launching the app
* Make sure your database user has `SELECT`, `INSERT`, `UPDATE`, and `DELETE` privileges
* Terminal color output may not display on all systems (especially on Windows CMD)

---

## 👨‍💻 Author

**Shehan Sulakshana**
[GitHub Profile](https://github.com/ShehanSulakshana)
