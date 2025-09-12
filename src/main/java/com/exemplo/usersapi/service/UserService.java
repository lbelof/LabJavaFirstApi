package com.exemplo.usersapi.service;

import com.exemplo.usersapi.dto.UserRequest;
import com.exemplo.usersapi.dto.UserResponse;
import com.exemplo.usersapi.model.User;
import com.exemplo.usersapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repo;
    public UserService(UserRepository repo) { this.repo = repo; }

    public List<UserResponse> list() {
        return repo.findAll().stream().map(this::toResp).toList();
    }

    public UserResponse get(Long id) {
        var u = repo.findById(id).orElseThrow(() -> new NotFound("User not found"));
        return toResp(u);
    }

    public UserResponse create(UserRequest req) {
        if (repo.existsByEmail(req.email())) throw new Conflict("Email already in use");
        var u = new User(req.name(), req.email());
        return toResp(repo.save(u));
    }

    public UserResponse update(Long id, UserRequest req) {
        var u = repo.findById(id).orElseThrow(() -> new NotFound("User not found"));
        if (!u.getEmail().equals(req.email()) && repo.existsByEmail(req.email()))
            throw new Conflict("Email already in use");
        u.setName(req.name());
        u.setEmail(req.email());
        return toResp(repo.save(u));
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) throw new NotFound("User not found");
        repo.deleteById(id);
    }

    private UserResponse toResp(User u) {
        return new UserResponse(u.getId(), u.getName(), u.getEmail());
    }

    public static class NotFound extends RuntimeException { public NotFound(String m){ super(m);} }
    public static class Conflict extends RuntimeException { public Conflict(String m){ super(m);} }
}
