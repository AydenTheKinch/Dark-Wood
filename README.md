# In a Dark Wood

## a survival-horror game.

*In a Dark Wood* is a survival-horror game. You awake in the middle of the woods with no knowledge of how you got there.
The woods are rotten, and the foliage is slowly being consumed by a chrome colored rot. Malformed faeries and other
similar creatures stalk the player, and most are hostile. Each is consumed by the same sickness that infects the
rest of the wood. The environment is on a grid that the player navigates using a user interface. Each space on the
grid has the potential for a combat encounter or puzzle. Items are hidden throughout the forest and can be discovered
and added to your inventory. Combat is conducted via a turn based system, and non-combat encounters require
you to make one of several choices that are influenced by the items you've acquired.
Some examples of these encounters are:
- You start to sink in a pit of rot obscured by fallen leaves.
- A man dangling from a tree asks you how he got here; he's missing his right hand.
- A light lands on top of a nearby cedar. The forest grow quiet.

These encounters are primarily text-based. If you die, you have to start from the beginning.

It is intended to be a short experience that players can complete in 30 minutes to an hour. It will likely be
enjoyed by people interested in text-based adventure games with weird stories and minor RPG elements.

This is my first game, and stems from a desire to make more. Some of the best games I've experienced have had extremely
simple graphics and non-existent budgets.  I wanted to make a game that was not too difficult to program, but also has a
lot of room for mystery and suspense. The story is inspired by my love of religion and psychology, and is hopefully
intriguing without relying on any kind of drawn-out exposition.

## User stories
- As a user, I want to navigate to the next location by ~~clicking the corresponding arrow in the UI~~ interacting with
  the UI.
- As a user, I want to have at least two decisions to make every round of combat that influence its outcome.
- As a user, I want to discover new items and add them to my inventory.
- As a user, I want to ~~open~~ view my inventory and inspect my current items.
- As a user, I want to save my items and my location.
- As a user, I want to reload a state that includes my items and my location.

# Instructions for Grader

- You can generate the first required action related to adding Xs to a Y by typing "pickup" and then typing "branch"
- You can generate the second required action related to adding Xs to a Y by typing "wake up", this will reset the game
  as if the player was killed, removing items that are marked as ephemeral (this should include branch).
- You can generate the third required action related to adding Xs to a Y by typing the name of an item. This will search
  your inventory for the item and add that item's description to the text area in the center of the screen.
- You can locate my visual component by looking on the right side of the screen. The picture changes depending on the
  player's position. It is also occasionally affected by other data.
- You can save the state of my application by pressing the save button to the left of the text field. You can not save
  in combat.
- You can reload the state of my application by closing and reopening the game. On the main menu, selected continue
  loads the most recent saved game.
- All other features are explained in game. Have fun!

# Phase 4: Task 2
Sun Apr 09 19:50:13 PDT 2023
branch added to inventory.


Sun Apr 09 19:50:29 PDT 2023
hanged man added to memory.


Sun Apr 09 19:50:36 PDT 2023
heart added to inventory.


Sun Apr 09 19:50:36 PDT 2023
a burning heart added to memory.


Sun Apr 09 19:50:52 PDT 2023
grief added to inventory.


Sun Apr 09 19:50:52 PDT 2023
killed the fairy added to memory.


Sun Apr 09 19:51:07 PDT 2023
death of the king added to memory.


Sun Apr 09 19:51:12 PDT 2023
touched the crown added to memory.


Sun Apr 09 19:51:12 PDT 2023
branch removed from inventory
heart removed from inventory


Sun Apr 09 19:51:12 PDT 2023
killed the fairy removed from memory
death of the king removed from memory

Process finished with exit code 0

# Phase 4: Task 3

- It is painfully clear that I should have used a singleton design method for the "loot" field,
  the "event" field, the Story class, the "map" field, and quite possibly the Player class as well. I instead just
  parameter piped the appropriate references through my code which lead to quite a large amount of plumbing. It would
  have been far more elegant to utilize the design method instead. I would have to change all references to these fields
  and all the getters and setters that allowed these references to be passed all throughout the project and instead
  replace it with straight forward getInstance() static methods keys to the respective static field. I in fact began to
  perform this refactoring but ran out of time before I was able to complete it.

- There are a large amount of dependencies on single objects in my UML class diagram and implementing this specific
  refactoring would make the whole thing a lot cleaner. It's just a matter of removing all parameter piping and 
  replacing all methods that get or set the respective objects with getInstance() methods. Additionally, my UML diagram
  is quite cluttered which means my coupling is pretty bad. I could probably move some methods into a different class so 
  that I don't have to use as many fields. This is probably the most easily rectified in the UI classes as each point to
  Encounter and Player multiple times when they can probably just get the information they need from a single more
  abstract class. 