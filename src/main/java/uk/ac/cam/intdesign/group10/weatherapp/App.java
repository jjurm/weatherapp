package uk.ac.cam.intdesign.group10.weatherapp;

import javax.swing.JFrame;

import uk.ac.cam.intdesign.group10.weatherapp.screen.Screen;

public abstract class App extends JFrame {

    Screen currentScreen;

    public abstract void changeScreen(Screen screen);

}
