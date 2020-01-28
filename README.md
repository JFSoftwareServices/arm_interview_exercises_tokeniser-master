# Exercise 2

### Running Tests
To run tests:
This project does not use Maven or any other build tool. To run the tests simply execute them in your favorite IDE.
I used Intellij to develop the project and run tests.

### Notes
Everything executes successfully on my mac - macOS Mojave 10.14.6. using Intellij IDEA Community 2019.1

I provided an implementation of the Stream interface, however I am not sure that this required as part of the solution.

In my implementation of the Stream interface, the read method blocks, if no character is available, 
until one becomes available. My implementation of this interface isn't used anywhere since the test use StringStream. 