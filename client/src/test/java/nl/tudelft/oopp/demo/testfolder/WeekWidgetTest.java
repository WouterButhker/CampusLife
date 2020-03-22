package nl.tudelft.oopp.demo.testfolder;

import nl.tudelft.oopp.demo.communication.AuthenticationCommunication;
import nl.tudelft.oopp.demo.entities.Weekdays;
import nl.tudelft.oopp.demo.widgets.WeekWidget;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WeekWidgetTest {

    WeekWidget weekWidget;

    @BeforeEach
    void doBeforeEach() {
        AuthenticationCommunication.login("admin", "admin");
    }

    /*
    @Test
    public void init() {
        weekWidget = new WeekWidget(new Weekdays());
    }

    @Test
    public void toStringTest() {
        System.out.println(weekWidget.getWeekDays().toString());
    }
     */
}
