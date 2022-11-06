package annotations;

/**
* Custom made Jira annotation
*/
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * Annotation class is an interface which has two required fields @Retention and @Target
 * Annotation should be forwarded to the listener who will handle annotation and set the
 * Jira ID to the specific test in specified conditions
 *
 * @Target(ElementType.TYPE) - Indicates the contexts in which an annotation type is applicable. The
 * declaration contexts and type contexts in which an annotation type may be applicable
 * (Type of elements it can be applied in our case - TYPE means it can be applicable on
 * Class, Interface and Enum). We need TYPE because in our framework one test is one class,
 * and we need annotation on class level
 *
 * ElementType.ANNOTATION_TYPE - annotation that can be added to annotation
 * ElementType.TYPE - annotation that can be added to Class, Interface and Enum
 * CONSTRUCTOR - annotation that can be added to constructor
 * FIELD - annotation that can be added to specific field, on attribute including enum constants
 * METHOD - annotation that can be added to methods
 * PACKAGE - annotation that can be added to packages
 *
 * @Retention(RetentionPolicy.RUNTIME)- it can be CLASS, RUNTIME, SOURCE, in which phase
 * the annotation is needed, we need annotation in runtime
 *
 * CLASS- it means that the annotation is required by the compiler at compile time,
 * but it is not required to be passed to the JVM for runtime
 *
 * RUNTIME - needed for the runtime or in the class file is written by the compiler
 * and will be retained and will be passed to the JVM during runtime
 *
 * SOURCE - will be ignored even when it is compiled
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Jira {
    // this means that field is mandatory
    String jiraID();
    // in case there is no specific person which is responsible for
    // Jira by default it is Regression Team
    String owner() default "RegressionTeam";
}
