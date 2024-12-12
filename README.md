# Wallet-Watch

**Wallet-Watch** is a personal budget management tool designed to help users efficiently track their **income**, **expenses**, and **savings goals**. Built with object-oriented programming principles and backed by a MySQL database, Wallet-Watch empowers users to make informed financial decisions.

---

## Features

1. **Income Management**:
   - Add income records with details like source, amount, and date
   - View all income records for a specific user
   - Update or delete existing income entries
   - Robust error handling and input validation

2. **Expense Tracking**:
   - Log and categorize expenses
   - View expense history by user
   - Update or delete expense records
   - Date validation and format checking

3. **Admin Features**:
   - View transactions for all users
   - Manage records across multiple users
   - Reset all tables functionality
   - User activity monitoring

4. **Error Handling**:
   - Comprehensive input validation
   - Date format verification (YYYYMMDD)
   - Graceful error recovery
   - User-friendly error messages via PrintBuilder

5. **User Experience**:
   - Clear menu navigation with PrintBuilder
   - Persistent retry on invalid inputs
   - Organized display of records
   - Balance checking functionality

---

## Project Structure
```
wallet-watch/
├── COMMANDS.md        # Command reference and workflows
├── README.md         # Project documentation
├── bin/              # Compiled .class files
├── db/
│   ├── schema.sql    # Database structure
│   └── reset_db.sql  # Reset and test data
├── lib/
│   └── mysql-connector-j-9.1.0.jar
└── src/
    ├── dao/
    │   ├── IncomeDAO.java
    │   └── ExpenseDAO.java
    ├── models/
    │   ├── Income.java
    │   └── Expense.java
    ├── services/
    │   ├── IncomeService.java
    │   └── ExpenseService.java
    ├── util/
    │   ├── Environment.java
    │   └── PrintBuilder.java
    ├── test/
    │   └── WalletWatchTest.java
    └── Main.java
```

---

## Getting Started

1a. **Extensions needed for VSCode**:
   - SQLTools (mtxr)
   - SQLTools MySQL/MariaDB/TiDB (mtxr)
   - Extension Pack for Java (vscjava)
   - PowerShell (ms-vscode)

1b. **Prerequisites**:
   - Java Development Kit (JDK)
   - Powershell
   - MySQL Server
   - MySQL Connector/J 9.1.0

2. **Clone the Repository**:
   ```bash
   git clone https://github.com/yourusername/wallet-watch.git
   cd wallet-watch
   ```

3. **Database Setup**:
   - Open `db/schema.sql`
   - CTRL+A then press Ctrl+E twice to execute
   - Open `db/reset_db.sql`
   - CTRL+A then press Ctrl+E twice to execute

4. **Compile the Code**:
   ```powershell
   javac -d bin src/Main.java src/dao/IncomeDAO.java src/dao/ExpenseDAO.java src/dao/TransactionDAO.java src/models/Income.java src/models/Expense.java src/models/Transaction.java
   ```
   Or use the PowerShell commands from [COMMANDS.md](COMMANDS.md):
   ```powershell
   .\run.ps1 compile
   ```

5. **Run the Application**:
   ```powershell
   java -cp "bin;lib\mysql-connector-j-9.1.0.jar" Main
   ```

6. **Run Tests**:
   ```powershell
   # Compile tests
   javac -d bin src/test/IncomeTest.java src/test/ExpenseTest.java src/models/Income.java src/models/Expense.java src/models/Transaction.java src/dao/IncomeDAO.java src/dao/ExpenseDAO.java src/dao/TransactionDAO.java
   
   # Run Income tests
   java -cp "bin;lib\mysql-connector-j-9.1.0.jar" test.IncomeTest
   
   # Run Expense tests
   java -cp "bin;lib\mysql-connector-j-9.1.0.jar" test.ExpenseTest
   ```

For more detailed commands and workflows, see `COMMANDS.md`.

---

## Core Design

### Object-Oriented Principles
- **Service Layer Pattern**: 
  - Business logic separated into IncomeService and ExpenseService
  - Each service handles its specific domain operations
  - Validation and error handling encapsulated in services

- **Builder Pattern**: 
  - PrintBuilder for consistent message formatting
  - Method chaining for readable code
  - Centralized output handling

- **Data Access Objects**: 
  - Database operations encapsulated in DAO classes
  - Clean separation of concerns
  - Consistent error handling

- **Environment Management**: 
  - Secure configuration handling
  - Database credentials protection
  - Centralized environment variables

### Key Components
- **PrintBuilder**: 
  - Consistent message formatting
  - Success/Error styling
  - Menu formatting
  - Prompt handling

- **Service Layer**: 
  - Transaction validation
  - Business rule enforcement
  - User permission checking
  - Error handling

- **DAO Layer**: 
  - CRUD operations
  - Data persistence
  - Query handling

- **Test Suite**: 
  - Consolidated testing in WalletWatchTest
  - Database operation verification
  - Edge case handling

---

## Project Status
- ✅ Core functionality complete
- ✅ PrintBuilder implementation
- ✅ Service layer separation
- ✅ Consolidated test suite
- ✅ Admin features
- ✅ Balance checking
- ✅ Error handling improvements

---

## Future Enhancements
1. **Database Operations**:
   - Batch operations
   - Transaction rollback
   - Query optimization

2. **User Interface**:
   - More detailed balance reports
   - Category summaries
   - Date range filtering

3. **Admin Features**:
   - User management system
   - Audit logging
   - Backup/restore functionality

---

## Target Audience
- College students
- Young professionals
- Budget-conscious individuals
- Anyone seeking a simple, effective financial tracking tool

---

## Commands Reference
[Commands](COMMANDS.md)

## License
[MIT License](LICENSE)