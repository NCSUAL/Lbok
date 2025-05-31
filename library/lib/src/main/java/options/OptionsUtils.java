package options;

import java.util.List;
import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.tools.Diagnostic.Kind;

import constant.GetterMsg;
import constant.nested.Status;

public class OptionsUtils {

    public static synchronized boolean hasOptions(ProcessingEnvironment processingEnv, Element element, Class<?> classInstance){
        for(AnnotationMirror annotationMirror: element.getAnnotationMirrors()){
            if(annotationMirror.getAnnotationType().toString().equals(Options.class.getCanonicalName())){
                for(Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : annotationMirror.getElementValues().entrySet()){
                    if(entry.getKey().getSimpleName().contentEquals("value")){

                        //entryValue 값은 javac의 List임
                        Object entryValue = entry.getValue().getValue();

                        if(entryValue instanceof List<?> list){
                            for(Object object: list){
                                if(object instanceof AnnotationValue){
                                    processingEnv.getMessager().printMessage(Kind.NOTE, GetterMsg.createMsg(Status.INFO,element.getSimpleName().toString().concat("은"),"생성 대상에서 제외시킵니다."));
                                    return ((AnnotationValue)object).getValue().toString().equals(classInstance.getCanonicalName());
                                }
                            }
                        }
                    }
                }
                return false;
            }
        }
        return false;
    }

}
