## Update Progress
### Folder Structure Plan
```
  src/
  └── com/
      └── game/
          ├── core/                 # Berisi class inti game
          │   ├── Game.java         # Class untuk mengatur aturan game
          │   ├── Card.java         # Class untuk representasi kartu
          │   ├── GameTimer.java    # Class untuk timer game
          │   └── Board.java        # Class untuk logika board game
          │
          ├── ui/                   # Berisi elemen GUI
          │   ├── GameWindow.java   # Main window class
          │   ├── MainMenu.java     # Class untuk layar menu utama
          │   ├── GamePanel.java    # Class untuk layar permainan
          │   └── Panel.java        # Interface untuk panel GUI
          │
          └── utils/                # Berisi kelas utility/helper
              └── GameUtils.java    # Helper class (opsional)
```


### Test Game Window
1. Go to the project folder: `cd /Flipsy`
2. Compile the java file: `javac src/com/game/core/*.java src/com/game/ui/*.java`
3. Go to src `cd src`
4. Run the GameWindow.java:  `java com.game.ui.GameWindow`
5. Shoulda look like this:
   ![WhatsApp Image 2024-11-20 at 23 51 34_d218ae5f](https://github.com/user-attachments/assets/02201457-8010-4069-9261-0ffda2f36dd3)

   If the name is not filled but user already click 'Mulai Permainan':
   ![image](https://github.com/user-attachments/assets/81400cda-2688-4941-ad20-8afd89fac385)

   The board and timer, card has not been added
   ![image](https://github.com/user-attachments/assets/2f7faf37-cb4c-45de-b907-cb0ca7170857)





