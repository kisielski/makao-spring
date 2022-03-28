package pl.makao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.makao.entity.Hand;
import pl.makao.repository.HandRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class HandService {

    private HandRepository repository;

    @Autowired
    public HandService(HandRepository repository) { this.repository = repository; }

    public List<Hand> findAll() { return repository.findAll(); }

    public Optional<Hand> findById(int id) { return repository.findById(id); }

    public int findFreeId() {
        int freeId = 1;
        while(repository.existsById(freeId) && freeId <= repository.count() + 1) freeId++;
        return freeId;
    }

    @Transactional
    public Hand create(Hand hand) { return repository.save(hand); }

    @Transactional
    public void delete(Hand hand) { repository.delete(hand); }

    @Transactional
    public void update(Hand hand) { repository.save(hand); }
}
