package nl.tudelft.oopp.demo.core;

import java.util.Stack;
import javafx.scene.Scene;

/**
 * This class helps give a structure to the navigation of different windows in the app.
 * To display a new window pushRoute() should be called with a Route.
 * To go back to the previous window call popRoute().
 */
public class RoutingScene extends Scene {
    private Stack<Route> routes;

    /**
     * Insantiates the RoutingScene with a base Route which can never be removed.
     * @param initialRoute the first route in the stack
     */
    public RoutingScene(Route initialRoute) {
        super(initialRoute.getRootElement());

        routes = new Stack<>();
        pushRoute(initialRoute);
    }

    /**
     * Adds a route to the stack and displays it.
     * @param route the route to be added and displayed
     */
    public void pushRoute(Route route) {
        routes.add(route);
        route.routingScene = this;
        setRoot(route.getRootElement());
    }

    /**
     * Removes the top most route and displays the one before it.
     * @throws Exception thrown when the last route is popped
     */
    public void popRoute() throws Exception {
        if (routes.size() == 1) {
            // TODO should not be a generic exception
            throw new Exception("Cannot pop last route");
        }

        routes.peek().routingScene = null;
        routes.pop();
        setRoot(routes.peek().getRootElement());
    }
}
