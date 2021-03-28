package com.utn2019.avanzada2.tp9.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import static javax.persistence.GenerationType.IDENTITY;
@Data
@NoArgsConstructor
@Entity
@Table(name = "phrases")
public class Phrase {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer phraseId;
   // @NotBlank
    private String sp;
 //   @NotBlank
    private String eng;
}
