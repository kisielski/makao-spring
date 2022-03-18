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

    public Optional<Hand> findByOwner(int owner) { return repository.findById(owner); }

    @Transactional
    public Hand create(Hand hand) { return repository.save(hand); }

    @Transactional
    public void delete(Hand hand) { repository.delete(hand); }
}
