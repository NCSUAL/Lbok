package constant.nested;

public enum AnnotationList {
    NoArgsConstructor("NoArgsConstructor");

    private final String annotationName;

    AnnotationList(final String annotationName){
        this.annotationName = annotationName;
    }

    @Override
    public String toString(){
        return this.annotationName;

    }
}
