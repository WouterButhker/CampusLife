package nl.tudelft.oopp.demo;

import nl.tudelft.oopp.demo.repositories.FoodOrderQuantityRepository;
import nl.tudelft.oopp.demo.repositories.FoodOrderRepository;
import nl.tudelft.oopp.demo.repositories.FoodRepository;
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

    //    @Test
    //    public void test() {
    //
    //        //FoodOrder foodOrder = new FoodOrder(99, "fix this", "fix this",1); /// TODO fix
    //        Food apple = new Food(1, 99, "apple", 1.5);
    //        Food cheesecake = new Food(2, 99, "cheesecake", 10.0);
    //
    //        foodRepository.save(apple);
    //        foodRepository.save(cheesecake);
    //
    //        User user = new User("user","pass", "Student");
    //        Building building = new Building(88, "Pulse", "Campus", "11:00-19:00",null);
    //        Restaurant restaurant = new Restaurant(1, "tosti", building,"Nice food");
    //        FoodOrder foodOrder = new FoodOrder(user, " today", "ASAP", restaurant);
    //        foodOrderRepository.save(foodOrder);
    //
    //        FoodOrderQuantity appleOrder = new FoodOrderQuantity(apple, foodOrder, 2);
    //
    //        FoodOrderQuantity cheesecakeOrder = new FoodOrderQuantity(cheesecake, foodOrder, 1);
    //
    //        foodOrderQuantityRepository.save(appleOrder);
    //
    //        foodOrderQuantityRepository.save(cheesecakeOrder);
    //
    //        /// TODO fix
    //        //assert foodRepository.count() == 2;
    //        //assert foodOrderQuantityRepository.count() == 2;
    //    }

}
