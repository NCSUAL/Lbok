package noArgs;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCClassDecl;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Name;
import common.AstreeEditor;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.tools.Diagnostic.Kind;




public class NoArgsConstructorUtils {

    public static Consumer<JCTree.JCClassDecl> createStrategy(final AstreeEditor astreeEditor, final ProcessingEnvironment processingEnvironment){

        return new Consumer<JCTree.JCClassDecl>(){

            @Override
            public void accept(JCClassDecl jcClassDecl) {

                ListBuffer<JCTree> jcClass = new ListBuffer<>();

                for(JCTree jcTree: jcClassDecl.getMembers()){
                    if(jcTree instanceof JCTree.JCMethodDecl jcMethod){
                        
                        //생성자가 아닐 경우에 Class 구성요소에 포함시킨다.
                        if(Objects.nonNull(jcMethod.getReturnType())){
                            jcClass.append(jcMethod);
                        }

                        //생성자 경우, 기본 생성자가 아닌 경우에 Class 구성요소에 포함시킨다.
                        if(Objects.isNull(jcMethod.getReturnType()) && (Objects.isNull(jcMethod.params)) ){
                            jcClass.append(jcMethod);
                        }
                    }
                    else{
                            jcClass.append(jcTree);
                     }
                }

            //기본 값은 PUBLIC


            //클래스에 붙은 어노테이션 가져오기

            jcClass.append(createNoArgsConstructor(astreeEditor, jcClassDecl.name));
            
            jcClassDecl.defs = jcClass.toList();

        }};
    }

    private static JCTree.JCMethodDecl createNoArgsConstructor(final AstreeEditor astreeEditor, final Name name){
        return astreeEditor.getTreeMaker().
        MethodDef(
            astreeEditor.getTreeMaker().Modifiers(Flags.PUBLIC),
            name,
            null,
            List.nil(),
            List.nil(),
            List.nil(),
            astreeEditor.getTreeMaker().Block(0, List.nil()),
            null
            );
    }


}
