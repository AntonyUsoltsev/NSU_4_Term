package ru.nsu.ccfit.usoltsev.myUtils;

import lombok.Getter;

import java.io.Serializable;

public record Message<T>(@Getter String typeMessage, @Getter T content) implements Serializable {}
