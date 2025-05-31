package setter;

import java.util.Set;
import java.util.function.Consumer;

import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.JCTree.JCClassDecl;
import com.sun.tools.javac.tree.JCTree.JCMethodDecl;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Names;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;

import com.google.auto.service.AutoService;

import common.AstreeEditor;
import constant.SetterMsg;
import constant.Warning;
import constant.nested.Status;
import exception.NotValidElementKind;
import options.OptionsUtils;

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

        super.init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {

        AstreeEditor astreeEditor = new AstreeEditor( (JavacProcessingEnvironment) processingEnv);

        astreeEditor.setStrategy(createStrategy(astreeEditor));

        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Setter.class);
        for(Element element: elements){
            switch (element.getKind()) {
                case CLASS, FIELD:
                    astreeEditor.setTree(element);
                    break;
            
                default:
                    throw new NotValidElementKind(element.getKind()+"는","타겟 대상이 아닙니다.");
            }
        }

        return true;
    }

    private Consumer<JCClassDecl> createStrategy(AstreeEditor astreeEditor){
        TreeMaker treeMaker = astreeEditor.getTreeMaker();
        Names names = astreeEditor.getNames();

        return new Consumer<JCClassDecl>(){
            @Override
            public void accept(JCClassDecl jClassDecl) {
                for(JCTree jcTree: jClassDecl.getMembers()){
                    if(jcTree instanceof JCTree.JCVariableDecl field){
                        
                        if(OptionsUtils.hasOptions(processingEnv,field.sym,Setter.class)){
                                continue;                           
                        }

                        JCMethodDecl jcMethodDecl = createSetter(names, treeMaker, field);

                        jClassDecl.defs = jClassDecl.defs.append(jcMethodDecl);
                    }
                }
            }
        };
    }


    private JCMethodDecl createSetter(Names names, TreeMaker treeMaker, JCTree.JCVariableDecl jctree){
        StringBuffer sb = new StringBuffer();
        String memberName = jctree.name.toString();
        String methodName = sb.append("set").append(memberName.substring(0,1).toUpperCase()).append(memberName.substring(1)).toString();

        processingEnv.getMessager().printMessage(Kind.NOTE, SetterMsg.createMsg(Status.INFO, "memberName의","setter 메서드를 생성합니다"));
        /**
         *  파라미터가 있는 메서드이기 때문에, 필요한 파라미터를 설정해주어야 한다.  
         *  treeMaker의 Parm 메서드를 사용
         * owner는 사용하지 않는 인수이므로 null을 넣어준다.(3번째 인자)
         * 
         *  jctree.vartype.type -> jctree( element 노드)에 해당하는 타입을 꺼낸다
         */

         JCTree.JCVariableDecl setterParameter = treeMaker.Param(names.fromString(memberName), jctree.vartype.type, null);

        return treeMaker.MethodDef(
            treeMaker.Modifiers(Flags.PUBLIC),
            names.fromString(methodName), 
            treeMaker.TypeIdent(TypeTag.VOID),
            List.nil(),
            List.of(setterParameter),
            List.nil(),
            treeMaker.Block(0, List.of(treeMaker.Exec(treeMaker.Assign( treeMaker.Select(treeMaker.Ident(names.fromString("this")), jctree.name), treeMaker.Ident(jctree.name))))),
            null);
    }
}
