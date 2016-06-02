package com.tlitiwwhtmi.annotation;

import java.lang.annotation.*;

/**
 * Created by saver on 16/5/31.
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented

public @interface PrimayKey {

    String idGenerator() default "com.tlitiwwhtmi.uuid.fileUuid.FileUUIDGenerator";

}
