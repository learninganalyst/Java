Java Swing Practice

This repository contains beginner Java practice files, with a focus on learning Java Swing GUI components.

Project Description

This project demonstrates how to create a simple Java Swing window using JFrame, FlowLayout, and JComboBox.

The current example creates a dropdown menu with animal options:

pig
horse
dog

When the user selects an item from the dropdown menu, the program prints:

the selected item
the selected index
Files
Java/
│
├── MyFrame.java
└── README.md
Main Java Concepts Practiced
Concept	Description
JFrame	Creates the main GUI window
JComboBox	Creates a dropdown menu
FlowLayout	Arranges components in a simple left-to-right layout
ActionListener	Detects user actions
actionPerformed()	Runs code when the user selects an item
Example Code Feature

The dropdown menu is created using an array:

String[] animals = {"pig", "horse", "dog"};

comboBox = new JComboBox(animals);
comboBox.addActionListener(this);

When a user selects an animal, this code prints the result:

System.out.println(comboBox.getSelectedItem());
System.out.println(comboBox.getSelectedIndex());
How to Run the Program

To run this program, you need a main() method. You can create another file named Main.java:

public class Main {
    public static void main(String[] args) {
        new MyFrame();
    }
}

Then run Main.java in Eclipse.
