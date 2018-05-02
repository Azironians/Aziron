package spring;

public final class SpringEngine implements Spring{

    private String string;

    @Override
    public final String getString() {
        return string;
    }

    public final void setString(final String string) {
        this.string = string;
    }
}