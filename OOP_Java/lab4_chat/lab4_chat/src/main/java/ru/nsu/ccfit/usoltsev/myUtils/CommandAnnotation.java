package ru.nsu.ccfit.usoltsev.myUtils;

import java.lang.annotation.*;

@Target(value = {ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
@Inherited
public @interface CommandAnnotation { }
