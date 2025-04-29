package constant.nested;

public enum Status {

    INFO("[INFO]"),
    WARNING("[WARING]"),
    ERROR("[ERROR]");
    
    private final String status;
    
    Status(final String status){
        this.status = status;
    }

    @Override
    public String toString(){
        return this.status;
    }
}
