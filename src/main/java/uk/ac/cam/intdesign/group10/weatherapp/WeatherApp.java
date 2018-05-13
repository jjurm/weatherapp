package uk.ac.cam.intdesign.group10.weatherapp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import uk.ac.cam.intdesign.group10.weatherapp.screen.HomeScreen;
import uk.ac.cam.intdesign.group10.weatherapp.screen.Screen;

import static javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW;

public class WeatherApp extends JFrame {

    private Screen currentScreen;
    private JPanel screenHolder;

    public void changeScreen(Screen screen) {
        screenHolder.removeAll();
        currentScreen = screen;
        screenHolder.add(screen.getRootComponent());

        // repaint panel and recalculate layout of components
        screenHolder.repaint();
        screenHolder.revalidate();
    }

    private WeatherApp() {
        super("Weather app");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(600, 240);
        setSize(300, 400);

        createComponents();
        changeScreen(new HomeScreen(this));

        // exit on ESC
        getRootPane().getInputMap(WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "EXIT");
        getRootPane().getActionMap().put("EXIT", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    private void createComponents() {
        Container pane = getContentPane();

        screenHolder = new JPanel();
        screenHolder.setLayout(new BorderLayout(0, 0));
        pane.add(screenHolder, BorderLayout.CENTER);

    }


    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new WeatherApp();
            }
        });
    }

}
