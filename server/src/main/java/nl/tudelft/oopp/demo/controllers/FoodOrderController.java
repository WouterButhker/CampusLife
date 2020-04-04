package nl.tudelft.oopp.demo.controllers;

import java.util.List;

import nl.tudelft.oopp.demo.entities.User;
import nl.tudelft.oopp.demo.entities.food.FoodOrder;
import nl.tudelft.oopp.demo.entities.food.FoodOrderQuantity;
import nl.tudelft.oopp.demo.repositories.FoodOrderQuantityRepository;
import nl.tudelft.oopp.demo.repositories.FoodOrderRepository;
import nl.tudelft.oopp.demo.repositories.FoodRepository;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        FoodOrder createdOrder = foodOrderRepository.save(foodOrder);

        //FoodOrder foodOrderFix = new FoodOrder(userRepository.findById(foodOrder.getId()).get(),
        //     foodOrder.getDate(), foodOrder.getTimeSlot(), foodOrder.getRestaurant());
        //FoodOrder createdOrder = foodOrderRepository.save(foodOrderFix);

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

        foodOrder.setId(createdOrder.getId());
        return foodOrder;
    }

    @GetMapping(value = "/user/{userId}")
    List<FoodOrder> getFoodOrders(@PathVariable Integer userId) {
        User user = userRepository.findById(userId).get();

        return foodOrderRepository.allFoodOrdersOfUser(user);
    }

    @GetMapping(value = "/{foodOrderId}")
    List<FoodOrderQuantity> getFoods(@PathVariable Integer foodOrderId) {
        FoodOrder foodOrder = foodOrderRepository.findById(foodOrderId).get();

        return foodOrderQuantityRepository.allFoodQuantitiesOfOrder(foodOrder);
    }
}
