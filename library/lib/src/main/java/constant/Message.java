package constant;

import java.util.Arrays;

import constant.nested.Status;

public interface Message {

    //create etc message
    default String createMsg(final Status status, final String... message){
        StringBuffer sb = new StringBuffer();
        sb.append(status.toString()).append(" ");
        Arrays.stream(message).forEach(t -> sb.append(t).append(" "));

        return sb.toString();
    }

}
