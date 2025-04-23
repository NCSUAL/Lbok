package constant;


import constant.nested.Content;
import constant.nested.Status;

public enum Warning {

    WARNING_NOTMATCH_JDKVERSION(Status.WARNING,Content.NOTMATCH_JDKVERSION);

    private final Status status;
    private final Content content;

    Warning(final Status status, final Content content){
        this.status = status;
        this.content = content;
    }

    public String getMessage(){
        return this.status.toString().concat(": ").concat(this.content.toString());
    }

    @Override
    public String toString(){
        return getMessage();
    }
}
