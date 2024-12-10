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
   - Update/delete capabilities for any user
   - User activity monitoring

4. **Error Handling**:
   - Comprehensive input validation
   - Date format verification
   - Graceful error recovery
   - User-friendly error messages

5. **User Experience**:
   - Clear menu navigation
   - Persistent retry on invalid inputs
   - Organized display of records
   - Intuitive command structure

---

## Project Structure
```
FAST Project/
├── COMMANDS.md        # Command reference and workflows
├── README.md         # Project documentation
├── bin/              # Compiled .class files
├── db/
│   ├── schema.sql    # Database structure
│   ├── reset_db.sql  # Reset and test data
│   └── queries/      # SQL queries for reference
├── lib/
│   └── mysql-connector-j-9.1.0.jar
└── src/
    ├── dao/
    │   ├── TransactionDAO.java
    │   ├── IncomeDAO.java
    │   └── ExpenseDAO.java
    ├── models/
    │   ├── Transaction.java
    │   ├── Income.java
    │   └── Expense.java
    ├── test/
    │   ├── IncomeTest.java
    │   └── ExpenseTest.java
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
- **Encapsulation**: 
  - Financial data (Income/Expense) is encapsulated in model classes
  - Each model manages its own data validation
  - Database operations are encapsulated in specific DAO classes
  - User input handling is encapsulated in the Main class

- **Inheritance**: 
  - TransactionDAO serves as the parent class for database operations
  - IncomeDAO and ExpenseDAO inherit and extend base functionality
  - Common CRUD operations are reused through inheritance
  - Error handling patterns are inherited consistently

- **Polymorphism**: 
  - TransactionDAO methods work with different transaction types
  - Each DAO implements specific behavior for its model type
  - Generic database operations handle different data models
  - Error handling works uniformly across different operations

- **Abstraction**: 
  - DAO pattern abstracts database operations from business logic
  - Model classes abstract data representation
  - Menu system abstracts user interaction
  - Error handling abstracts input validation and recovery

### Design Patterns
- **Modularity**: 
  - Clear separation between models, DAOs, and UI
  - Each class has a single responsibility
  - Easy to maintain and extend functionality

- **Error Handling**:
  - Exception handling at appropriate levels
  - User-friendly error messages
  - Retry mechanisms for invalid inputs

- **Data Access**:
  - DAO pattern for database operations
  - Transaction management
  - Prepared statements for security

### Database Integration
- MySQL database with relational integrity
- Data Access Objects for clean database interaction
- Comprehensive test data and reset capabilities

---

## Target Audience
- College students
- Young professionals
- Budget-conscious individuals
- Anyone seeking a simple, effective financial tracking tool

---

## Future Enhancements

1. **API Integration**:
   - Connect with public APIs for real-time recommendations
   - Fetch bank transactions for automated updates

2. **Web-Based UI**:
   - Build an interactive front-end
   - Mobile-responsive design

3. **Advanced Analytics**:
   - Trend analysis and spending comparisons
   - Predictive analysis for financial planning

---

## Project Status
- **Current Focus**: Income management module
- **In Progress**: Core functionality and testing
- **Planned**: Expense tracking and savings goals

---
## Commands Reference
[Commands](COMMANDS.md)

## License
[MIT License](LICENSE)