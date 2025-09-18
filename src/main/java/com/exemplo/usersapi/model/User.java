package com.exemplo.usersapi.model;

import jakarta.persistence.*;
import org.hibernate.id.GUIDGenerator;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) private String name;
    @Column(nullable = false) private String email;
    @Column(nullable = true) private String telefone;

    public User() {}
    public User(String name, String email, String telefone) {
        this.name = name;
        this.email = email;
        this.telefone = telefone;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}
