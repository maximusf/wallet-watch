# Wallet-Watch

**Wallet-Watch** is a personal budget management tool designed to help users efficiently track their **income**, **expenses**, and **savings goals**. Built with object-oriented programming principles and backed by a MySQL database, Wallet-Watch empowers users to make informed financial decisions.

---

## Features

1. **Income Management**:
   - Add income records with details like source, amount, and date.
   - View all income records for a specific user.
   - Update or delete existing income entries.

2. **Expense Tracking** (Planned):
   - Log and categorize expenses to track spending habits.

3. **Savings Goals** (Planned):
   - Set financial goals and track progress.

4. **Trend Analysis** (Planned):
   - Visualize spending and saving patterns over time.

5. **Proactive Recommendations** (Future):
   - Suggest cost-saving measures based on spending trends.

---

## Project Design

### Object-Oriented Principles
- **Encapsulation**: Financial data is modeled as classes like `Income`, ensuring data integrity through validation.
- **Modularity**: DAOs handle database interactions, keeping business logic separate.
- **Scalability**: The modular design allows for easy addition of new features.

### Database Integration
- MySQL is used to store user, income, and expense data.
- The `income` table tracks:
  - ID
  - User ID
  - Amount
  - Source
  - Date

---

## How to Run

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/yourusername/wallet-watch.git
   cd wallet-watch
   ```

2. **Set Up the Database**:
   - Run `MySQL Local.session.sql` to create the required tables.

3. **Compile and Run the Project**:
   ```bash
   javac -d bin src/**/*.java
   java -cp bin Main
   ```

4. **Interact with the CLI**:
   - Add income records, view records, and more.

---

## Future Enhancements

1. **API Integration**:
   - Connect with public APIs (e.g., GasBuddy) for real-time recommendations.
   - Fetch bank transactions for automated updates.

2. **Web-Based UI**:
   - Build an interactive front-end for enhanced user experience.

3. **Advanced Analytics**:
   - Provide trend analysis and spending comparisons.

---

## Project Status
- Current Focus: Income management module.
- Planned: Expense tracking and savings goals.

---

## License
[MIT License](LICENSE)
