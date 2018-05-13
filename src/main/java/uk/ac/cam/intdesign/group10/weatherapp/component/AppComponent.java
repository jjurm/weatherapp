package uk.ac.cam.intdesign.group10.weatherapp.component;

import javax.swing.JComponent;

/**
 * Interface for any modular component in the app. Usage might be as follows:
 *
 * <code>
 *     class ToolbarImpl extends JPanel implements Toolbar {
 *         public JComponent getRootComponent() { return this; }
 *         ...
 *     }
 * </code>
 */
public interface AppComponent {

    /**
     * As the class represents a physical component in the app, this method returns the (root)
     * component that should be displayed. If subclasses extend something like JPanel, they can just
     * return themselves (i.e. return this;)
     * @return
     */
    public JComponent getRootComponent();

}
