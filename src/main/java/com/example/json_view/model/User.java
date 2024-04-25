package com.example.json_view.model;

import com.example.json_view.dto.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.UserSummary.class)
    private Long id;
    @NotEmpty(message = "Имя пользователя не может быть пустым")
    @JsonView(Views.UserSummary.class)
    private String name;
    @Email(message = "Некорректный адрес электронной почты")
    @JsonView(Views.UserSummary.class)
    private String email;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonView(Views.UserDetails.class)
    private List<Order> orders;
}
