# run.ps1
# Copyright 2024 maximusf
# Manages compilation and execution of the Wallet-Watch project

param(
    [string]$command = "help"
)

function Show-Help {
    Write-Host @"
Wallet-Watch Command Runner

Usage: .\run.ps1 <command>

Commands:
    compile     - Compile all Java files
    run         - Run the main application
    test        - Run the test suite
    reset       - Reset the database
    clean       - Remove compiled files
    all         - Compile, reset DB, and run tests
    help        - Show this help message
"@ -ForegroundColor Cyan
}

function Start-Compilation {
    Write-Host "Compiling source files..." -ForegroundColor Yellow
    
    $sourceFiles = @(
        "src/util/Environment.java",
        "src/models/Transaction.java",
        "src/models/Income.java",
        "src/models/Expense.java",
        "src/dao/TransactionDAO.java",
        "src/dao/IncomeDAO.java",
        "src/dao/ExpenseDAO.java",
        "src/test/IncomeTest.java",
        "src/test/ExpenseTest.java",
        "src/Main.java"
    )
    
    & javac -d bin -cp "lib\mysql-connector-j-9.1.0.jar" $sourceFiles
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "Compilation successful!" -ForegroundColor Green
        return $true
    } else {
        Write-Host "Compilation failed!" -ForegroundColor Red
        return $false
    }
}

function Start-MainProgram {
    if (-not (Test-Path "bin/Main.class")) {
        Write-Host "Program not compiled. Running compilation first..." -ForegroundColor Yellow
        if (-not (Start-Compilation)) { return }
    }
    Write-Host "Running main program..." -ForegroundColor Yellow
    & java -cp "bin;lib\mysql-connector-j-9.1.0.jar" Main
}

function Start-Tests {
    if (-not (Test-Path "bin/test/IncomeTest.class") -or -not (Test-Path "bin/test/ExpenseTest.class")) {
        Write-Host "Tests not compiled. Running compilation first..." -ForegroundColor Yellow
        if (-not (Start-Compilation)) { return }
    }
    Write-Host "Running tests..." -ForegroundColor Yellow
    
    Write-Host "`nRunning Income Tests:" -ForegroundColor Cyan
    & java -cp "bin;lib\mysql-connector-j-9.1.0.jar" test.IncomeTest
    
    Write-Host "`nRunning Expense Tests:" -ForegroundColor Cyan
    & java -cp "bin;lib\mysql-connector-j-9.1.0.jar" test.ExpenseTest
}

function Reset-Database {
    Write-Host "Resetting database..." -ForegroundColor Yellow
    Write-Host "Please run reset_db.sql in Cursor or MySQL Workbench" -ForegroundColor Cyan
    $response = Read-Host "Have you reset the database? (y/n)"
    if ($response -eq 'y') {
        Write-Host "Database reset confirmed" -ForegroundColor Green
    } else {
        Write-Host "Database not reset. Please reset before continuing" -ForegroundColor Red
    }
}

function Remove-CompiledFiles {
    Write-Host "Cleaning compiled files..." -ForegroundColor Yellow
    if (Test-Path "bin") {
        Remove-Item -Recurse -Force "bin"
        Write-Host "Cleaned successfully" -ForegroundColor Green
    } else {
        Write-Host "Nothing to clean" -ForegroundColor Cyan
    }
}

function Start-All {
    Write-Host "Running full build and test sequence..." -ForegroundColor Yellow
    if (Start-Compilation) {
        Reset-Database
        Start-Tests
        Write-Host "All tasks completed" -ForegroundColor Green
    }
}

switch ($command.ToLower()) {
    "compile" { Start-Compilation }
    "run" { Start-MainProgram }
    "test" { Start-Tests }
    "reset" { Reset-Database }
    "clean" { Remove-CompiledFiles }
    "all" { Start-All }
    default { Show-Help }
}
