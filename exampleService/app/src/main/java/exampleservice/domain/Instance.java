package exampleservice.domain;
import noArgs.NoArgsConstructor;

@NoArgsConstructor
public class Instance {
    private int integer;
    private String string;
    private Character character;

    public Instance(final int integer, final String string, final Character character){
        this.integer = integer;
        this.string = string;
        this.character =character;
    }


    public void test(){
        
    }
}
