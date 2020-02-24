package nl.tudelft.oopp.demo.core;

import javafx.scene.Parent;

public abstract class Route {
    protected RoutingScene routingScene;

    public RoutingScene getRoutingScene() {
        return routingScene;
    }

    public abstract Parent getRootElement();
}
