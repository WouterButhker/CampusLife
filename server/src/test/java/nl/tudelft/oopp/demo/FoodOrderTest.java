package nl.tudelft.oopp.demo;

import nl.tudelft.oopp.demo.entities.Food;
import nl.tudelft.oopp.demo.entities.reservation.food.FoodOrder;
import nl.tudelft.oopp.demo.entities.reservation.food.FoodOrderQuantity;
import nl.tudelft.oopp.demo.repositories.FoodOrderQuantityRepository;
import nl.tudelft.oopp.demo.repositories.FoodOrderRepository;
import nl.tudelft.oopp.demo.repositories.FoodRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class FoodOrderTest {

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    FoodOrderRepository foodOrderRepository;

    @Autowired
    FoodOrderQuantityRepository foodOrderQuantityRepository;

    @Test
    public void test() {
        Food apple = new Food(1, 99, "apple", 1.5);
        Food cheesecake = new Food(2, 99, "cheesecake", 10.0);
        foodRepository.save(apple);
        foodRepository.save(cheesecake);

        FoodOrder foodOrder = new FoodOrder(1, " today", "ASAP", 20);
        foodOrderRepository.save(foodOrder);

        FoodOrderQuantity appleOrder = new FoodOrderQuantity(apple, foodOrder, 2);
        FoodOrderQuantity cheesecakeOrder = new FoodOrderQuantity(cheesecake, foodOrder, 1);
        foodOrderQuantityRepository.save(appleOrder);
        foodOrderQuantityRepository.save(cheesecakeOrder);

        assert foodRepository.count() == 2;
        assert foodOrderQuantityRepository.count() == 2;
    }

}
