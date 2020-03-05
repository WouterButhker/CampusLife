package nl.tudelft.oopp.demo.entities;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String name;
    private Integer buildingCode;
    private String openingHours;
    private List<String> menu;

    public Restaurant(String name,
                      Integer buildingCode,
                      String openingHours,
                      List<String> menu) {
        this.name = name;
        this.buildingCode = buildingCode;
        this.openingHours = openingHours;
        this.menu = new ArrayList<String>();

        for(int i = 0 ; i < menu.size() ; i++) {
            this.menu.add(menu.get(i));
        }
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Integer getBuildingCode() { return buildingCode; }
    public void setBuildingCode(Integer buildingCode) { this.buildingCode = buildingCode; }

    public String getOpeningHours() {
        return openingHours;
    }
    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public List<String> getMenu() { return menu; }
    public void setMenu(List<String> menu) {
        this.menu = new ArrayList<String>();

        for(int i = 0 ; i < menu.size() ; i++) {
            this.menu.add(menu.get(i));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Restaurant)) {
            return false;
        }
        Restaurant restaurant = (Restaurant) o;
        return name.equals(restaurant.name)
                && buildingCode == restaurant.buildingCode
                && openingHours.equals(restaurant.openingHours);
    }
}
