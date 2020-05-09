package testUtilities;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class AnnotationTransformer implements IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation annotation, Class aClass, Constructor constructor, Method method) {
        annotation.setRetryAnalyzer(Retry.class);
    }
}
