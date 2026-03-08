# 🏦 Java ATM Simulation System

A **console-based ATM simulation** built in Java demonstrating core OOP principles, Collections Framework, File Handling, and Exception Handling.

---

## 🚀 Features

| Feature | Description |
|---|---|
| 👤 User Authentication | Card number + PIN login |
| 🔒 Security | Max 3 PIN attempts, account lock |
| 💰 Check Balance | View current account balance |
| 📥 Deposit | Add money to account |
| 📤 Withdraw | Withdraw with balance validation |
| 🔄 Transfer | Transfer to another card |
| 📜 Transaction History | Last 10 transactions shown |
| 💾 Data Persistence | Saved via Java File Serialization |

---

## 🗂️ Project Structure

```
ATM-Simulation-System/
├── src/
│   └── main/
│       ├── ATMMain.java         ← Entry point
│       ├── ATMSession.java      ← Session and menu logic
│       ├── Bank.java            ← Bank data management
│       └── BankAccount.java     ← Account model and operations
└── README.md
```

---

## 🧪 Default Test Accounts

| Card Number | PIN | Account Holder | Balance |
|---|---|---|---|
| 1111222233334444 | 1234 | Alice Johnson | $5000.00 |
| 5555666677778888 | 5678 | Bob Smith | $3000.00 |
| 9999000011112222 | 9999 | Carol White | $7500.00 |

---

## ▶️ How to Run

```bash
# Compile all files
javac src/main/*.java -d out/

# Run the program
java -cp out ATMMain
```

---

## 🧠 Java Concepts Used

- **OOP** - Encapsulation, Abstraction, Classes and Objects
- **Collections Framework** - ArrayList, HashMap
- **File Handling** - Object Serialization (ObjectInputStream / ObjectOutputStream)
- **Exception Handling** - try-catch blocks throughout
- **Menu-driven Program** - Console-based switch-case navigation
