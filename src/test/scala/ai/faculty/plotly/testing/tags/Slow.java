package ai.faculty.plotly;

import java.lang.annotation.*;
import org.scalatest.TagAnnotation ;

@TagAnnotation
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Slow {}
