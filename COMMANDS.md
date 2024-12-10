# Wallet-Watch Commands Reference

## Database Commands
Use these in Cursor by opening the .sql file and pressing Ctrl+E twice:
```sql
-- Initialize database structure
db/schema.sql

-- Reset database and add test data
db/reset_db.sql
```

## Compilation Commands
Run these in PowerShell from project root:
```powershell
# Compile main application
javac -d bin src/Main.java src/dao/IncomeDAO.java src/models/Income.java

# Compile tests
javac -d bin src/test/IncomeTest.java src/models/Income.java src/dao/IncomeDAO.java
```

## Execution Commands
Run these in PowerShell from project root:
```powershell
# Run main application
java -cp "bin;lib\mysql-connector-j-9.1.0.jar" Main

# Run tests
java -cp "bin;lib\mysql-connector-j-9.1.0.jar" test.IncomeTest
```

## Project Structure
```
FAST Project/
├── bin/              # Compiled .class files
├── db/
│   ├── schema.sql    # Database structure
│   └── reset_db.sql  # Reset and test data
├── lib/
│   └── mysql-connector-j-9.1.0.jar
└── src/
    ├── dao/
    │   └── IncomeDAO.java
    ├── models/
    │   └── Income.java
    ├── test/
    │   └── IncomeTest.java
    └── Main.java
```

## Common Workflows

### First Time Setup
1. Run schema.sql (Ctrl+E twice)
2. Run reset_db.sql (Ctrl+E twice)
3. Compile main application
4. Run main application

### Development Cycle
1. Make code changes
2. Compile affected files
3. Run reset_db.sql if needed
4. Run tests
5. Run main application

### Testing
1. Run reset_db.sql
2. Compile tests
3. Run tests

## Notes
- Always ensure MySQL server is running
- Use correct version of mysql-connector in commands
- Run commands from project root directory