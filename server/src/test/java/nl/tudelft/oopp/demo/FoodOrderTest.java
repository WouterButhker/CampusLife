package nl.tudelft.oopp.demo;

import nl.tudelft.oopp.demo.entities.food.Food;
import nl.tudelft.oopp.demo.entities.food.FoodOrder;
import nl.tudelft.oopp.demo.entities.food.FoodOrderQuantity;
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

        FoodOrder foodOrder = new FoodOrder(99, 99, 1);
        Food apple = new Food(1, 99, "apple", 1.5);
        Food cheesecake = new Food(2, 99, "cheesecake", 10.0);
        FoodOrderQuantity appleOrder = new FoodOrderQuantity(apple, foodOrder, 2);
        FoodOrderQuantity cheesecakeOrder = new FoodOrderQuantity(cheesecake, foodOrder, 1);

        foodRepository.save(apple);
        foodRepository.save(cheesecake);
        foodOrderRepository.save(foodOrder);
        foodOrderQuantityRepository.save(appleOrder);
        foodOrderQuantityRepository.save(cheesecakeOrder);

        assert foodRepository.count() == 2;
        assert foodOrderQuantityRepository.count() == 2;
    }

}
