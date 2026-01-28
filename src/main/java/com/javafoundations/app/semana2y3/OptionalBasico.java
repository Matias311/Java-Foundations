package com.javafoundations.app.semana2y3;

import java.util.Optional;

public class OptionalBasico {

  Optional<String> normalize(String s) {
    if (s == null) {
      return Optional.empty();
    }
    String t = s.trim();
    return t.isEmpty() ? Optional.empty() : Optional.of(t);
  }
}
