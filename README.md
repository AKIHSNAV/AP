# STICK HERO
- Clone of the game stick hero using javafx and oops concepts

## How The Game Works
- Press play to start
- The length of the stick increases till the mouse is pressed
- The sticks falls down when the mouse is pressed
- Press space for flipping the hero
- 1 cherry is required for reviving the player
- Game can be puased, resumed, and restarted.

## Screenshots
![stickherodemo](https://github.com/AKIHSNAV/StickHero/assets/119172207/8ec49e14-f7be-4065-ab07-28dc7289d74d)


## Object-Oriented Programming Concepts in the Code:

### 1. *Inheritance:*
   - *Pillar Class:* The Pillar class extends the JavaFX Rectangle class, inheriting properties and methods.

### 2. *Encapsulation:*
   - *Private Members:* Both classes use private variables to encapsulate data and control access. For instance, width in the Pillar class and sticksufficient in the Stick class are private.

### 3. *Static Members:*
   - *Pillar Class:* It contains static variables (minWidth, maxWidth, etc.) and a static method (generateRandomDistance()).
   - *Stick Class:* It doesn't use static members in this snippet.

### 4. *Composition:*
   - *Pillar Class:* The Pillar class has a Rectangle member (pillar) as part of its composition.

### 5. *Dependency Injection:*
   - *Pillar Class:* The shiftPillar method takes an AtomicInteger and a HelloController as parameters, demonstrating dependency injection.

### 6. *Timeline and Animation:*
   - *Pillar Class:* The shiftPillar method uses JavaFX Timeline for animation, creating a smooth transition between pillars.
   - *Stick Class:* The Stick class uses a Timeline (growthTimeline) for animating stick growth.
   - The player rotates while falling

### 11. *Exception Handling:*
   - Exception handling has been done as required

### 12. *AtomicInteger:*
   - *Pillar Class:* The shiftPillar method uses AtomicInteger (count) for atomic updates in a multi-threaded environment.

## Design Patterns
   - Factory Method Pattern:
      The generatePillar method is a candidate for the Factory Method Pattern. It appears to be part of a class (Pillar1) responsible for creating instances of a Pillar object. This method encapsulates the object creation process, allowing subclasses or different implementations to alter the type of Pillar object created. 
   - Observer Pattern:
      In the SwitchToGameOverScreen method, the code is using a form of the Observer Pattern. It sets up an observer relationship between the HelloController instance and the gameover.fxml view. The controller listens for changes in the game state and updates the displayscore text field in the gameover.fxml view accordingly
   - Singleton Pattern:
      Singleton has been used to create instance of controller class which has been passed in different methods
      for example
      ```java
         private static HelloController controller = null;
         public static HelloController getInstance() throws IOException {
         if (controller==null) {
            controller = new HelloController();
         } return controller;
      }
      ````

## Junit 
   - Few Junits tests have been added as well. 

## Serialization and Deserialization 
   - Serialization and Deserialization has been used whenever save game and load game is prompted. Whenever save game is prompted, the score and the number of cherries get stored in a file which are then retrived when a previously saved game gets loaded.
   java
   public static void saveProgress(int cherries, int score) {
        System.out.println("in here");
        Saveprogress saveprogress = new Saveprogress(cherries, score);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("progress.txt"))) {
            oos.writeObject(saveprogress);
            System.out.println("Progress saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Saveprogress loadProgress() {
        Saveprogress saveprogress = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("progress.txt"))) {
            saveprogress = (Saveprogress) ois.readObject();
            System.out.println("Progress loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return saveprogress;
    }
    
   
   ## Assumptions
- When an action is taking place, no other inputs should be given. This includes increasing the length of the stick when walking, pausing the game when the hero is walking.
- The help and sound buttons on the main screen are dummy buttons
