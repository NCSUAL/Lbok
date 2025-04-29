package constant;

import java.util.Arrays;

import constant.nested.Content;
import constant.nested.Status;

public enum GetterMsg {
    INFO_INITIALIZE_GETTER(Status.INFO,Content.COMPLETE_INITIALIZE_GETTER),
    ERROR_INITIALIZE_GETTER(Status.ERROR,Content.NOTCOMPLETE_INITIALIZE_GETTER);

    private final Status status;
    private final Content content;

    GetterMsg(final Status status, final Content content){
        this.content = content;
        this.status = status;
    }

    //create etc message
    public static String createMsg(final Status status, final String... message){
        StringBuffer sb = new StringBuffer();
        sb.append(status.toString()).append(" ");
        Arrays.stream(message).forEach(t -> sb.append(t).append(" "));

        return sb.toString();
    }

    public String getMessage(){
        return this.status.toString().concat(" ").concat(this.content.toString());
    }

    @Override
    public String toString(){
        return getMessage();
    }
}
