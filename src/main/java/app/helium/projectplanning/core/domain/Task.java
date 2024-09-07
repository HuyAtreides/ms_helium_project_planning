package app.helium.projectplanning.core.domain;


import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Task extends Issue {

}
