package setter;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;

import com.google.auto.service.AutoService;

import constant.SetterMsg;
import constant.Warning;

@AutoService(Processor.class)
@SupportedAnnotationTypes("setter.Setter")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class SetterProcessor extends AbstractProcessor{

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {

        //setter processor 초기화 로그 출력
        processingEnv.getMessager().printMessage(Kind.NOTE, SetterMsg.INFO_INITIALIZE_SETTER.getMessage());

        //제공하는 JDK버전과 사용하는 JDK 버전이 다를 때 로그 출력
        if(!processingEnv.getSourceVersion().equals(SourceVersion.RELEASE_17)){
            processingEnv.getMessager().printMessage(Kind.NOTE, Warning.WARNING_NOTMATCH_JDKVERSION.getMessage());
        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> arg0, RoundEnvironment arg1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'process'");
    }

}
