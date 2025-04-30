# Dungeon Adventure

**Dungeon Adventure** is a simple adventure game written in Java, where the player explores dungeons, battles enemies, collects items (health potions and armor), and solves puzzles. This project demonstrates modular programming, object-oriented design, inheritance, and randomization concepts.

## Features


- **Map Generation:**  
  A randomly generated square map with different tile types:
  - 10% of the tiles contain items (health potions or armor)
  - 10% of the tiles contain enemies
  - 10% of the tiles contain puzzles
  - The remaining 70% are empty spaces

- **Enemies:**  
  Fight enemies that cause damage. If the player survives, they gain experience points (XP).

- **Items:**  
  Randomly receive health potions (Small, Medium, or Large) or various types of armor (Rusty, Gold, or Godness). The item drop is determined randomly.

- **Puzzles:**  
  Solve simple math puzzles to earn XP. Incorrect answers incur penalties, such as lost health.

- **Difficulty Settings:**  
  The game can be configured for different difficulty levels, affecting the damage range and XP rewards.

## Project Structure

- **`character`**  
  Contains character-related classes:
  - `Character.java` – Abstract class defining common behaviors
  - `Player.java` – Represents the player (with inventory, XP management, and more)
  - `Enemy.java` – Represents enemies

- **`game`**  
  Contains game management and logic classes:
  - `GameManager.java` – The primary controller managing game flow, player movement, and the map
  - `EncounterManager.java` – Manages player interactions with map elements (enemies, items, puzzles, exits)
  - `DifficultySettings.java` – Stores and configures game difficulty parameters
  - `Entity.java` – Base class for all entities (characters, items, etc.)

- **`items`**  
  Contains item-related classes:
  - `Item.java` – Abstract class for items
  - **Subpackage `items.armor`:**
    - `Armor.java` – Abstract class for armor
    - `RustyArmor.java`, `GoldArmor.java`, `GodnessArmor.java` – Concrete armor types
  - **Subpackage `items.potion`:**
    - `Potion.java` – Abstract class for health potions
    - `SmallHealthPotion.java`, `MediumHealthPotion.java`, `LargeHealthPotion.java` – Concrete health potion classes

- **`main`**  
  Contains:
  - `Main.java` – The entry point to the application that starts the game

## Requirements

- **Java:**  
  The project is built with Java (version 11 or newer is recommended).

- **IDE:**  
  Use IntelliJ IDEA, Eclipse, or your preferred Java development environment.

- **Compilation:**  
  Ensure the `src` directory is marked as the Sources Root in your IDE.

## Installation and Running the Application

1. **Clone or Download the Project:**  
   Clone this repository or download the project files.

2. **Open the Project:**  
   Open the project in your IDE while preserving the folder structure.

3. **Configure the Sources Root:**  
   Mark the `src` folder as your Sources Root (in IntelliJ IDEA: right-click on `src` → **Mark Directory as > Sources Root**).

4. **Build the Project:**  
   Use the build functionality (e.g., **Build > Rebuild Project** in IntelliJ IDEA) to compile the project.

5. **Run the Application:**  
   Execute `Main.java` located in the `main` package to launch the game.

## How to Play

- **Movement:**  
  Use keys such as `w`, `s`, `a`, `d` (or commands like `2w` for moving two steps) to navigate the map.

- **Interactions:**  
  The game automatically processes interactions based on the tile type:
  - **E:** Engage in combat with an enemy.
  - **I:** Receive a random item drop (health potion or armor).
  - **?:** Solve a math puzzle.
  - **X:** Exit the dungeon.

- **Inventory:**  
  Press `i` to view the player's current inventory.

- **Quit:**  
  Type `q` to exit the game.

## Future Enhancements

- Expand the inventory system to allow item usage during combat.
- Introduce additional enemy types and more advanced combat mechanics.
- Add more interactive puzzles and richer gameplay experiences.
- Implement save/load functionality to maintain game progress.

## Reporting Issues

If you encounter any issues or have suggestions for improvements, please create an issue or contact the project maintainers.

---

Feel free to explore the code and contribute to further development!
