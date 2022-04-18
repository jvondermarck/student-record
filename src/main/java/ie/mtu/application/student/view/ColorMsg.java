package ie.mtu.application.student.view;

public enum ColorMsg {
    ERROR("Error", "#d74343"),
    SUCCESS("Success", "#60d93b");

    private final java.lang.String msg;
    final String color;

    ColorMsg(String text, String color) {
        this.msg = text;
        this.color = color;
    }

    public String getColor()
    {
        return this.color;
    }
}
