package ru.job4j.pooh;

public class Req {
    private final String method;
    private final String mode;
    private final String theme;
    private final String text;
    private final int id;

    private Req(String method, String mode, String theme, String text, int id) {
        this.method = method;
        this.mode = mode;
        this.theme = theme;
        this.text = text;
        this.id = id;
    }

    public static Req of(String content) {
        String[] lines = content.split("\n");
        String method = lines[0].substring(0, lines[0].indexOf(' '));
        String modeThemeText = lines[0].substring(lines[0].indexOf('/') + 1);
        String mode = modeThemeText.substring(0, modeThemeText.indexOf('/'));
        String theme;
        String themeAndId = modeThemeText.substring(modeThemeText.indexOf('/') + 1, modeThemeText.indexOf(' '));
        theme = themeAndId.substring(0, themeAndId.indexOf('/'));
        int id = Integer.valueOf(themeAndId.substring(themeAndId.indexOf('/') + 1));
        String text = "";
        if (method.equals("POST")) {
            text = lines[lines.length - 1];
        }
        return new Req(method, mode, theme, text, id);
    }

    public String method() {
        return method;
    }

    public String mode() {
        return mode;
    }

    public String theme() {
        return theme;
    }

    public String text() {
        return text;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Req{"
                + "method='" + method + '\''
                + ", mode='" + mode + '\''
                + ", theme='" + theme + '\''
                + ", text='" + text + '\''
                + ", id=" + id
                + '}';
    }
}
