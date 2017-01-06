package net.pd.ethraa.integration.jackson;

public class Views {

    public static class Base {
    }

    public static class Public extends Base {
    }

    public static class Group extends Base {
    }

    public static class LoginSuccess extends Public {
    }

    public static class Messaging extends Base {
    }

    public static class UserMessage extends Messaging {
    }

    public static class AdminMessage extends Messaging {
    }

}
