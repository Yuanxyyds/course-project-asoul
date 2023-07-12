## UofT FoodTruck - Group Asoul

Detailed Documentation at "Phase 2 Design Document.pdf"

We designed a FoodTruck ordering program in which the user can create an account to
interact with the program and place orders from a food truck through the program, as
well as sell food items through their food truck.
The project essentially consists of two major parts: the backend and the frontend. The
backend is the core of our program and includes all the design patterns and
functionalities that one can expect from a food ordering system. Some examples
include:
- In a login system, the user can register with an account, and log in with the
username and password anytime after the account is created. The login system
also checks for incorrect or non-existing credentials and returns error messages.
- In a Rating system, the buyer can rate an order anytime after the order is being
marked as complete (which is executed by the seller of the order). Then the
rating will be reflected in the rating of the food truck as an average of the total.
- A market sorter, the market which consists of food trucks can be displayed
either by default (random), by rating, or by name.
- Serialization, all data of user account, food truck, and orders are saved to an
external file, constructed upon program entering and saved upon program exiting
… or other functionaries such as being able to
- manage & edit food truck and its menu
- change user information (nickname, password, add money...)
- review order history lists

These are just some examples of the functionalities of our program. A particular note to
our user account design is that an account can act as both buyer and seller provided
the user manually activates their food truck if they want to “become” a seller. Then, the
user can freely edit their food truck.

Now proceeding to the front end of the program. Our front end also consists of two
designs: the originally implemented fully-functional command-line interface and a
partially implemented Android app that acts slightly differently. (All files of the Android
app are contained in the branch “Android”)

- The command-line interface is our primary front-end display which is interacted
with the command line in Intellij, in particular scenes, we have provided a “help”
function that the user can call to see the list of commands available to use.
- The Android interface is our secondary front-end display and is interacted
through an emulator. We have made the app almost fully functional with the
exception of implementing dynamically viewing the market and viewing order
history, and place order (so it includes everything the command line can do
including serialization except the ones mentioned) .
