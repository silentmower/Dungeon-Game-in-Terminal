Dungeon Adventure
Dungeon Adventure is a simple adventure game written in Java in which the player explores dungeons, encounters enemies, collects items (health potions and armor), and solves puzzles. The project serves as a demonstration of modular programming, object-oriented design, inheritance, and the use of randomization.
Features
Map Generation: A randomly generated square map where different tiles are assigned as follows:
10% of the tiles contain items (health potions or armor),
10% contain enemies,
10% contain puzzles,
The remaining 70% are empty spaces.
Enemies: Engage in battles with enemies that inflict damage, allowing the player to gain experience points (XP) if they survive.
Items: Random item drops include various health potions (Small, Medium, Large) and different types of armor (Rusty, Gold, Godness). The drop chance is randomized to give a varied gameplay experience.
Puzzles: Solve simple math puzzles to earn XP. Incorrect answers may result in penalties, such as a loss of health.
Difficulty Settings: The game allows configuration of game parameters (damage range, XP awards, etc.) based on the selected difficulty level.
Project Structure
The project is organized into the following packages:
character Contains character-related classes:
Character.java – An abstract class defining shared behaviors,
Player.java – Represents the player (with an inventory, XP management, etc.),
Enemy.java – Represents enemy characters.
game Contains game management and logic classes:
GameManager.java – The main controller that manages the game loop, player movement, and the map,
EncounterManager.java – Manages encounters between the player and map elements (enemies, items, puzzles, exit),
DifficultySettings.java – Manages the configuration of game difficulty settings,
Entity.java – A base class for all entities (from which characters and items derive).
items Contains item-related classes:
Item.java – An abstract class for game items,
Subpackage items.armor:
Armor.java – An abstract class for armor items,
RustyArmor.java, GoldArmor.java, GodnessArmor.java – Concrete armor types.
Subpackage items.potion:
Potion.java – An abstract class for health potions,
SmallHealthPotion.java, MediumHealthPotion.java, LargeHealthPotion.java – Concrete types of potions.
main Contains:
Main.java – The entry point for the application that launches the game.
Requirements
Java: The project is written in Java (Java 11 or later is recommended).
IDE: You can use IntelliJ IDEA, Eclipse, or any other preferred Java development environment.
Compilation: Ensure that the src folder is marked as the Sources Root in your IDE.
Installation and Execution
Download the Project: Clone or download the project files from the repository.
Open the Project: Open the project in your IDE, ensuring that the folder structure is preserved.
Configure the Sources Root: Mark the src folder as your Sources Root (in IntelliJ IDEA: right-click on src → Mark Directory as > Sources Root).
Build the Project: Use your IDE’s build functionality (e.g., Build > Rebuild Project in IntelliJ IDEA) to compile the project.
Run the Application: Execute the Main.java file located in the main package.
How to Play
Movement: Use keys such as w, s, a, d (or commands like 2w for moving two spaces) to navigate through the map.
Interactions: The game automatically processes interactions based on the type of tile encountered:
E: Engage in a fight with an enemy.
I: Receive a random item drop (either a health potion or armor).
?: Solve a math puzzle.
X: Exit the dungeon.
Inventory: Press i to view the current inventory of the player.
Quit: Type q to exit the game.
Future Enhancements
Expand the inventory system to allow using items during combat.
Introduce additional types of enemies and more complex combat mechanics.
Enhance interactions and puzzles for a richer gameplay experience.
Implement a save/load system to retain game progress.
Reporting Issues
If you encounter any issues or have suggestions for improvements, please feel free to report them or contact the project maintainers.
