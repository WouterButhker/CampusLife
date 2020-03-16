package nl.tudelft.oopp.demo.testfolder;

import nl.tudelft.oopp.demo.entities.Weekdays;
import nl.tudelft.oopp.demo.widgets.WeekWidget;
import org.junit.jupiter.api.Test;

public class WeekWidgetTest {

    WeekWidget weekWidget;

    @Test
    public void init() {
        weekWidget = new WeekWidget(new Weekdays());
    }

    @Test
    public void toStringTest() {
        System.out.println(weekWidget.getWeekDays().toString());
    }
}
