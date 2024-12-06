# VSCode Extensions Required:
- SQLTools (mtxr)
- SQLTools MySQL/MariaDB/TiDB (mtxr)

---

# **Wallet-Watch**

**Wallet-Watch** is a personal budget management tool designed to help individuals efficiently track their income, expenses, and savings goals. It’s ideal for students, professionals, and anyone who wants an intuitive, structured way to manage their finances and make informed financial decisions.

---

## **Overview**
Wallet-Watch provides a streamlined experience for tracking financial data, offering a simple interface for managing:

- **Income**: Log sources (e.g., salary, freelance, investments) with details like amount, source, and date.
- **Expenses**: Categorize spending (e.g., food, rent, transportation) to understand trends.
- **Savings Goals**: Set financial targets, track progress, and view timelines to achieve specific goals.

---

## **Target Audience**
Wallet-Watch primarily appeals to:
1. **College Students**: Those starting to manage their finances and seeking a straightforward tool.
2. **Young Professionals**: Individuals balancing multiple income sources and monthly expenses.
3. **Budget-Conscious Individuals**: Anyone wanting to monitor financial activity and make better spending decisions.

The design prioritizes simplicity and robustness to appeal to users seeking efficient personal finance management.

---

## **How It Works**
1. **Data Models**:
   - The application employs **object-oriented programming (OOP)** principles, using classes like `Income`, `Expense`, and `SavingsGoal` to represent financial data.
   - DAOs (Data Access Objects) handle database interactions, ensuring modular and maintainable code.

2. **Database Integration**:
   - A MySQL database securely stores user data, enabling efficient access and updates.
   - The database structure includes `users`, `income`, `expenses`, and `savings_goals` tables, ensuring data integrity through relational links.

3. **User Interaction**:
   - The current version uses a **command-line interface (CLI)** for input, allowing users to:
     - Add, edit, and view financial records.
     - Check savings progress.
     - Generate simple reports.

4. **Scalability**:
   - The modular design allows for future enhancements, such as a web-based UI using frameworks like ReactJS.

---

## **Features**
### **1. Income Tracking**
- Log income sources with descriptions, amounts, and dates.
- View summaries of monthly or yearly income.

### **2. Expense Management**
- Categorize expenses to identify spending trends.
- Generate reports to visualize category-based spending.

### **3. Savings Goals**
- Set target amounts and deadlines.
- Track progress toward financial goals with clear contributions.

### **4. User-Friendly Reports (Planned)**
- Summarize income vs. expenses over a selected period.
- Highlight savings progress and spending patterns.

---

## **Project Requirements**
Wallet-Watch meets key requirements of a software development project:
1. **Demonstrates Object-Oriented Principles**:
   - **Encapsulation**: Financial entities (e.g., `Income`, `Expense`) are self-contained with attributes and methods.
   - **Inheritance**: Shared properties and behaviors are abstracted efficiently.
   - **Polymorphism**: Methods adapt dynamically to display or manipulate data based on context.

2. **Database Usage**:
   - Implements a relational database (MySQL) for storing and retrieving financial data.

3. **Real-World Utility**:
   - Provides a practical, relatable solution for financial management.

4. **Expandability**:
   - Modular design supports future features like predictive analysis and web-based interfaces.

---

## **Why Wallet-Watch is Unique**
- **Core Simplicity**: Focuses on essential features for financial tracking without unnecessary complexity.
- **Scalable Design**: Built with OOP and MySQL, ensuring it can grow with user needs.
- **Educational Value**: Serves as an excellent demonstration of OOP, database integration, and CLI design.

---

## **Summary**
Wallet-Watch is more than a budget tracker—it’s a tool for building financial literacy and habits. By emphasizing functionality and scalability, it satisfies the requirements of a solid software project while delivering real-world value to users managing their finances.