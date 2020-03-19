package nl.tudelft.oopp.demo.controllers;

import nl.tudelft.oopp.demo.entities.Food;
import nl.tudelft.oopp.demo.entities.reservation.food.FoodOrder;
import nl.tudelft.oopp.demo.entities.reservation.food.FoodOrderQuantity;
import nl.tudelft.oopp.demo.repositories.FoodOrderQuantityRepository;
import nl.tudelft.oopp.demo.repositories.FoodOrderRepository;
import nl.tudelft.oopp.demo.repositories.FoodRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/foodOrder")
public class FoodOrderController {

    @Autowired
    FoodOrderRepository foodOrderRepository;

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    FoodOrderQuantityRepository foodOrderQuantityRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * Adds a new food order to the database.
     * @param foodOrder the food order
     * @return the saved food order
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    FoodOrder addFoodOrder(@RequestBody FoodOrder foodOrder) {
        System.out.println(foodOrder);
        FoodOrder foodOrderFix = new FoodOrder(userRepository.findById(foodOrder.getId()).get(),foodOrder.getDate(), foodOrder.getTimeSlot(),foodOrder.getRestaurant());
        FoodOrder createdOrder = foodOrderRepository.save(foodOrderFix);

        // Create all food junctions
        for (List<Integer> pairs : foodOrder.getFoodsList()) {
            int foodId = pairs.get(0);
            int quantity = pairs.get(1);

            FoodOrderQuantity ding = new FoodOrderQuantity(
                    foodRepository.getOne(foodId),
                    createdOrder,
                    quantity
            );

            foodOrderQuantityRepository.save(ding);
        }

        foodOrderFix.setId(createdOrder.getId());
        return foodOrderFix;
    }
}
