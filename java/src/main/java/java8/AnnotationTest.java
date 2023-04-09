package java8;

@MyAnnotation("1")
@MyAnnotation("2")
public class AnnotationTest {

    void show(@MyAnnotation("3") String value){

    }
}
