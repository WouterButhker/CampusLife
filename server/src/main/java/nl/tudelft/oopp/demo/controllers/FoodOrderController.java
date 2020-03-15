package nl.tudelft.oopp.demo.controllers;

import java.util.List;
import nl.tudelft.oopp.demo.entities.FoodOrder;
import nl.tudelft.oopp.demo.entities.FoodOrderJunction;
import nl.tudelft.oopp.demo.repositories.FoodJunctionRepository;
import nl.tudelft.oopp.demo.repositories.FoodOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/foodOrder")
public class FoodOrderController {

    @Autowired
    private FoodOrderRepository foodOrderRepository;
    @Autowired
    private FoodJunctionRepository foodJunctionRepository;

    /**
     * Adds a new food order to the database.
     * @param foodOrder the food order
     * @return the saved food order
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    FoodOrder addFoodOrder(@RequestBody FoodOrder foodOrder) {
        FoodOrder createdOrder = foodOrderRepository.save(foodOrder);

        // Create all food junctions
        for (List<Integer> pairs : foodOrder.getFoodsList()) {
            int foodId = pairs.get(0);
            int quantity = pairs.get(1);

            FoodOrderJunction junction = new FoodOrderJunction(
                    null,
                    createdOrder.getId(),
                    foodId,
                    quantity
            );
            foodJunctionRepository.save(junction);
        }

        foodOrder.setId(createdOrder.getId());
        return foodOrder;
    }
}
