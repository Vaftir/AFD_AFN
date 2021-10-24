
import java.util.List;

public class State {
    public String Initial;//""
    public String Ending;//""
    public String ID; //""
    //public String nome;//""



    public State(String ID, String I, String F) {
        this.ID = ID;
        this.Initial = I;
        this.Ending =  F; 
    }

    public State() {
        this.ID = "";
        this.Initial = "";
        this.Ending =  ""; 
    }

    public String pString(int i) {
        if(this.Initial== "I"){
            return ("\n\t\t<state id=\"" + i + "\" name=\"q"+i+"\">&#13;\n\t\t\t<x>0</x>&#13;\n\t\t\t<y>0</y>&#13;\n\t\t\t<label>"+this.ID+"</label>&#13;\n\t\t\t<initial/>&#13;\n\t\t</state>&#13;");
        }else if(this.Ending =="f"){
            return ("\n\t\t<state id=\"" + i+ "\" name=\"q"+i+"\">&#13;\n\t\t\t<x>0</x>&#13;\n\t\t\t<y>0</y>&#13;\n\t\t\t<label>"+this.ID+"</label>&#13;\n\t\t\t<final/>&#13;\n\t\t</state>&#13;");
        }
        return ("\n\t\t<state id=\"" + i + "\" name=\"q"+i+"\">&#13;\n\t\t\t<x>0</x>&#13;\n\t\t\t<y>0</y>&#13;\n\t\t\t<label>"+this.ID+"</label>&#13;\n\t\t</state>&#13;");
		
	} 

    public void setInitial(String Initial){ this.Initial = Initial;}
    public String getInitial(){return this.Initial;}
    public void setEnding(String Ending){ this.Ending = Ending;}
    public String getEnding(){return this.Ending;}
    public void setID(String ID) {this.ID = ID;}
    public String getID(){return this.ID;}

    public String getFinal() {
        return this.Ending;
    }

    public String getInicial() {
        return this.Initial;
    }


    

    

    



}