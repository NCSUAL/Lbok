package getter;
import java.util.Set;
import java.util.function.Consumer;

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
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCClassDecl;
import com.sun.tools.javac.tree.JCTree.JCExpression;
import com.sun.tools.javac.tree.JCTree.JCMethodDecl;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Names;

import common.AstreeEditor;
import constant.GetterMsg;
import constant.Warning;
import constant.nested.Status;


@SupportedSourceVersion(SourceVersion.RELEASE_17)
@SupportedAnnotationTypes("getter.Getter")
@AutoService(Processor.class)
public class GetterProcessor extends AbstractProcessor{

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        
        //getter processor 초기화 로그 출력
        processingEnv.getMessager().printMessage(Kind.NOTE, GetterMsg.INFO_INITIALIZE_GETTER.getMessage());
        
        //제공하는 JDK버전과 사용하는 JDK 버전이 다를 때 로그 출력
        if(!processingEnv.getSourceVersion().equals(super.getSupportedSourceVersion())){
            processingEnv.getMessager().printMessage(Kind.WARNING,Warning.WARNING_NOTMATCH_JDKVERSION.getMessage());
        }

        super.init(processingEnv);
   }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        AstreeEditor astreeEditor = new AstreeEditor((JavacProcessingEnvironment)processingEnv);

        astreeEditor.setStategy(createStrategy(astreeEditor));

        for(Element element: roundEnv.getElementsAnnotatedWith(Getter.class)){
            if(element.getKind().equals(ElementKind.CLASS)){
                astreeEditor.setTree(element);
            }
        }
        return true;
    }

    private Consumer<JCClassDecl> createStrategy(AstreeEditor astreeEditor){
        TreeMaker treeMaker = astreeEditor.getTreeMaker();
        Names names = astreeEditor.getNames();

        return new Consumer<JCTree.JCClassDecl>() {
            @Override
            public void accept(JCTree.JCClassDecl jcClassDecl){
                for(JCTree jctree: jcClassDecl.getMembers()){
                    if(jctree instanceof JCTree.JCVariableDecl){
                        JCMethodDecl jcMethodDecl = createGetter(treeMaker, names, (JCVariableDecl)jctree);
                        jcClassDecl.defs = jcClassDecl.defs.append(jcMethodDecl);
                    }
                }
            }
        };
    }

    private JCMethodDecl createGetter(TreeMaker treeMaker, Names names, JCVariableDecl tree){
        StringBuffer sb = new StringBuffer();
        String memberName = tree.name.toString();
        String methodName = sb.append("get").append(memberName.substring(0,1).toUpperCase())
        .append(memberName.substring(1)).toString();
        
        processingEnv.getMessager().printMessage(Kind.NOTE, GetterMsg.createMsg(Status.INFO,memberName+"의","getter 메서드를 생성합니다."));

        return treeMaker.MethodDef(
            treeMaker.Modifiers(Flags.PUBLIC), 
            names.fromString(methodName),
            (JCExpression)tree.getType(), 
            List.nil(), 
            List.nil(),
            List.nil(),
            treeMaker.Block(0, List.of(treeMaker.Return(treeMaker.Ident(tree.getName())))),
            null);
    }

}
