package org.comrades.springtime.security.jwt;

public interface JwtFactory<U, T> {
    U create(T object);
}
