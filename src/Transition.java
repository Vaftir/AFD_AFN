public class Transition {
    public String To;
    public String From;
    public String Read;

    //
    public void setTo(String To) {
        this.To = To;
    }

    public String printTransition() {
        return ("\n\t\t<transition>&#13;\n\t\t\t<from>" + this.From + "</from>&#13;\n\t\t\t<to>" + this.Read
                + "</to>&#13;\n\t\t\t<read>" + this.Read + "</read>&#13;\n\t\t</transition>&#13;");
    }

    public Transition() {
        this.To = "";
        this.From = "";
        this.Read = "";
    }

    public Transition(String To, String From, String Read) {
        this.To = To;
        this.From = From;
        this.Read = Read;
    }

    public void setFrom(String From) {
        this.From = From;
    }

    public void setRead(String read) {
        this.Read = read;
    }

    public String getTo() {
        return this.To;
    }

    public String getFrom() {
        return this.From;
    }

    public String getRead() {
        return this.Read;
    }

}