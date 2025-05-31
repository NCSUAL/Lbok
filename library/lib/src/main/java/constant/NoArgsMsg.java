package constant;

import constant.nested.Content;
import constant.nested.Status;

public enum NoArgsMsg implements Message{
    INFO_INITIALIZE_NoARGS(Status.INFO,Content.COMPLETE_INITIALIZE_NOARGS);

    private final Status status;
    private final Content content;

    NoArgsMsg(final Status status, final Content content){
        this.status = status;
        this.content = content;
    }

    public String getMessage(){
        return this.status.toString().concat(" ").concat(this.content.toString());
    }

    @Override
    public String toString(){
        return getMessage();
    }
}
