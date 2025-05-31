package noArgs;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;

import com.google.auto.service.AutoService;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;

import common.AstreeEditor;
import constant.NoArgsMsg;
import constant.Warning;

@AutoService(Processor.class)
@SupportedAnnotationTypes("noArgs.NoArgsConstructor")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class NoArgsConstructorProcessor extends AbstractProcessor{

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
                //setter processor 초기화 로그 출력
        processingEnv.getMessager().printMessage(Kind.NOTE, NoArgsMsg.INFO_INITIALIZE_NoARGS.getMessage());

        //제공하는 JDK버전과 사용하는 JDK 버전이 다를 때 로그 출력
        if(!processingEnv.getSourceVersion().equals(SourceVersion.RELEASE_17)){
            processingEnv.getMessager().printMessage(Kind.NOTE, Warning.WARNING_NOTMATCH_JDKVERSION.getMessage());
        }
        super.init(processingEnv);
    }


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {

        AstreeEditor astreeEditor = new AstreeEditor((JavacProcessingEnvironment) processingEnv);

        astreeEditor.setStrategy(NoArgsConstructorUtils.createStrategy(astreeEditor, processingEnv));
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(NoArgsConstructor.class);

        for(Element element: elements){
            if(element.getKind().equals(ElementKind.CLASS)){
                astreeEditor.setTree(element);
            }
        }
        
        return true;
    }

}
