package mostwanted.repository;

import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.Racer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    Car findCarByRacer(Racer racer);
    Car findCarById(int id);
}
