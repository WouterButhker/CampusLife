package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.reservation.food.FoodOrder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/foodOrder")
public class FoodOrderController {



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

            FoodOrderQuantityKey junction = new FoodOrderQuantityKey(
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
