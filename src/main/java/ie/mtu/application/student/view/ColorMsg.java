package ie.mtu.application.student.view;

/**
 * This class is taking care of multiple enumeration color for different kind of situation to display a message
 */
public enum ColorMsg {
    ERROR("Error", "#d74343"),
    SUCCESS("Success", "#60d93b");

    private final java.lang.String msg;
    final String color;

    /**
     * @param text : the name of the enumeration
     * @param color : the hexadicamal color
     */
    ColorMsg(String text, String color) {
        this.msg = text;
        this.color = color;
    }

    /**
     * @return the hexadecimal color
     */
    public String getColor()
    {
        return this.color;
    }
}
