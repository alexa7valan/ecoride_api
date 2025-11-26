package co.edu.umanizales.ecorideapi2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Person {
    private String id;
    private String name;
    private String email;
    private String phone;
}
