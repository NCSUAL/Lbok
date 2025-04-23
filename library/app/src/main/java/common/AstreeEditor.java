package common;


import java.util.Objects;
import java.util.function.Consumer;

import javax.lang.model.element.Element;
import javax.tools.JavaFileObject;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.TreePath;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.tree.JCTree.JCClassDecl;
import com.sun.tools.javac.tree.JCTree.JCCompilationUnit;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Names;

public class AstreeEditor {
    private Trees trees;
    private Names names;
    private TreeMaker treeMaker;
    private Context context;
    private TreePathScanner<Object, CompilationUnitTree> scanner;

    public AstreeEditor(JavacProcessingEnvironment javacProcessingEnvironment){
        this.context = javacProcessingEnvironment.getContext();
        this.trees = Trees.instance(javacProcessingEnvironment);
        this.names = Names.instance(context);
        this.treeMaker = TreeMaker.instance(context);
    }

    public void setStategy(Consumer<JCClassDecl> jConsumer){
        this.scanner = new TreePathScanner<>(){
            @Override
            public Trees visitClass(ClassTree classTree, CompilationUnitTree compilationUnitTree){
                JCCompilationUnit jCompilationUnit = (JCCompilationUnit) compilationUnitTree;
                if(jCompilationUnit.sourcefile.getKind().equals(JavaFileObject.Kind.SOURCE)){
                    jCompilationUnit.accept(new TreeTranslator(){
                        @Override
                        public void visitClassDef(JCClassDecl tree){
                            super.visitClassDef(tree);;
                            jConsumer.accept(tree);
                        }
                    });
                }
                return trees;
            }
        };
    }

    public void setTree(Element element){
        if(Objects.nonNull(scanner)){
            TreePath path = trees.getPath(element);
            scanner.scan(path, path.getCompilationUnit());
        }
    }

    public TreeMaker getTreeMaker(){
        return this.treeMaker;
    }

    public Names getNames(){
        return this.names;
    }

}
