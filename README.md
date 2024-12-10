# Wallet-Watch

**Wallet-Watch** is a personal budget management tool designed to help users efficiently track their **income**, **expenses**, and **savings goals**. Built with object-oriented programming principles and backed by a MySQL database, Wallet-Watch empowers users to make informed financial decisions.

---

## Features

1. **Income Management**:
   - Add income records with details like source, amount, and date
   - View all income records for a specific user
   - Update or delete existing income entries

2. **Expense Tracking** (Planned):
   - Log and categorize expenses to track spending habits
   - Identify spending trends and patterns

3. **Savings Goals** (Planned):
   - Set financial goals and track progress
   - View timelines and progress reports

4. **Reports & Analysis** (Planned):
   - Generate summaries of income, expenses, and savings
   - Visualize spending and saving patterns over time

5. **Proactive Recommendations** (Future):
   - Suggest cost-saving measures based on spending trends
   - Provide personalized financial insights

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
│   └── queries/      # SQL queries for development
│       └── sample_queries.sql
├── lib/
│   └── mysql-connector-j-9.1.0.jar
└── src/
    ├── dao/          # Data Access Objects
    │   └── IncomeDAO.java
    ├── models/       # Data models
    │   └── Income.java
    ├── test/         # Test classes
    │   └── IncomeTest.java
    └── Main.java     # Application entry point
```

---

## Getting Started

1. **Prerequisites**:
   - Java Development Kit (JDK)
   - MySQL Server
   - MySQL Connector/J 9.1.0

2. **Clone the Repository**:
   ```bash
   git clone https://github.com/yourusername/wallet-watch.git
   cd wallet-watch
   ```

3. **Database Setup**:
   - Open `db/schema.sql` in Cursor
   - Press Ctrl+E twice to execute
   - Open `db/reset_db.sql`
   - Press Ctrl+E twice to execute

4. **Compile the Project**:
   ```powershell
   javac -d bin src/Main.java src/dao/IncomeDAO.java src/models/Income.java
   ```

5. **Run the Application**:
   ```powershell
   java -cp "bin;lib\mysql-connector-j-9.1.0.jar" Main
   ```

6. **Run Tests** (Optional):
   ```powershell
   javac -d bin src/test/IncomeTest.java src/models/Income.java src/dao/IncomeDAO.java
   java -cp "bin;lib\mysql-connector-j-9.1.0.jar" test.IncomeTest
   ```

For more detailed commands and workflows, see `COMMANDS.md`.

---

## Core Design

### Object-Oriented Principles
- **Encapsulation**: Financial data is modeled as classes with validation
- **Modularity**: Separate concerns between models, DAOs, and UI
- **Scalability**: Easy to add new features and functionality

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