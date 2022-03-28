package pl.makao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.makao.entity.Game;
import pl.makao.entity.Hand;
import pl.makao.repository.GameRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    private GameRepository repository;

    @Autowired
    public GameService(GameRepository repository) { this.repository = repository; }

    public List<Game> findAll() { return repository.findAll(); }

    public Optional<Game> findById(int gameId) { return repository.findById(gameId); }

    public int findFreeId() {
        int freeId = 1;
        while(repository.existsById(freeId) && freeId <= repository.count() + 1) freeId++;
        return freeId;
    }

    @Transactional
    public Game create(Game game) { return repository.save(game); }

    @Transactional
    public void delete(Game game) { repository.delete(game); }

    @Transactional
    public void update(Game game) { repository.save(game); }

    /*
    public boolean joinGame(Game game, int playerId) {
        return game.join(playerId);
    }
    */
}
