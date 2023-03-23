package main;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar{
    public JMenu menu, menu2;
    JMenuItem e1,e2;

    public MenuBar(){
        menu = new JMenu("Menu");

        e1 = new JMenuItem("Element 1");
        e2 = new JMenuItem("Element 2");

        menu.add(e1);
        menu.add(e2);

        menu2 = new JMenu("Menu");

        menu2.add(new JMenuItem("Oualid"));
        menu2.add(new JMenuItem("Mange tes morts"));

        this.add(menu);
        this.add(menu2);
        

    }
}
