package constant.nested;

public enum Content {

    COMPLETE_INITIALIZE_GETTER("Getter 어노테이션을 불러왔습니다."),
    NOTCOMPLETE_INITIALIZE_GETTER("Getter 어노테이션을 불러오지 못했습니다."),
    
    NOTMATCH_JDKVERSION("JDK가 대상 버전과 다릅니다. << JDK 17 >>");

    private final String content;
    
    Content(final String content){
        this.content = content;
    }

    @Override
    public String toString(){
        return this.content;
    }
}
