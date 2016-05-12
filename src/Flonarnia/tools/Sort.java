package Flonarnia.tools;
import Flonarnia.Flobjects.Flobject;

import java.util.Comparator;


public class Sort implements Comparator<Flobject> {
    public int compare(Flobject a, Flobject b) {
        return (int)(a.getBounds().getTranslateY() - b.getBounds().getTranslateY());
    }
}

