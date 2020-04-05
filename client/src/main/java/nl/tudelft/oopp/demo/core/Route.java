package nl.tudelft.oopp.demo.core;

import javafx.scene.Parent;

public abstract class Route {
    private RoutingScene routingScene;

    public RoutingScene getRoutingScene() {
        return routingScene;
    }

    protected void setRoutingScene(RoutingScene routingScene) {
        this.routingScene = routingScene;
    }

    public abstract Parent getRootElement();
}
