package pl.makao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.makao.entity.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {
}
