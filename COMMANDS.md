# Wallet-Watch Commands Reference

## Quick Start
Open PowerShell in the project root directory and run:
```powershell
# Show all available commands
.\run.ps1 help

# Typical workflow
.\run.ps1 compile  # Compile the code
.\run.ps1 reset    # Reset the database
.\run.ps1 test     # Run tests
.\run.ps1 run      # Run the application
```

## Available Commands

### Development Commands
```powershell
# Compile all Java files
.\run.ps1 compile

# Run the main application
.\run.ps1 run

# Run the test suite
.\run.ps1 test

# Reset the database (will prompt for confirmation)
.\run.ps1 reset

# Remove compiled files
.\run.ps1 clean

# Run everything (compile, reset DB, run tests)
.\run.ps1 all
```

### Database Commands
Use these in Cursor by opening the file and pressing Ctrl+E twice:
```sql
-- Initialize database structure
db/schema.sql

-- Reset database and add test data
db/reset_db.sql
```

## Project Structure
```
FAST Project/
├── bin/              # Compiled .class files
├── db/
│   ├── schema.sql    # Database structure
│   ├── reset_db.sql  # Reset and test data
│   └── queries/      # SQL queries for reference
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
1. Run schema.sql in Cursor (Ctrl+E twice)
2. Run reset_db.sql in Cursor (Ctrl+E twice)
3. `.\run.ps1 compile`
4. `.\run.ps1 run`

### Development Cycle
1. Make code changes
2. `.\run.ps1 compile`
3. `.\run.ps1 test`
4. `.\run.ps1 run`

### Testing
1. `.\run.ps1 reset`
2. `.\run.ps1 test`

## Notes
- Always run commands from project root directory
- MySQL server must be running
- Use Ctrl+E twice in Cursor for SQL files
