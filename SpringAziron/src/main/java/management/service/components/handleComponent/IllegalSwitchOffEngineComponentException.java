package management.service.components.handleComponent;

public final class IllegalSwitchOffEngineComponentException extends Exception {

    public IllegalSwitchOffEngineComponentException(){
        super();
    }

    public IllegalSwitchOffEngineComponentException(final String message){
        super(message);
    }

    public IllegalSwitchOffEngineComponentException(final Throwable cause){
        super(cause);
    }

    public IllegalSwitchOffEngineComponentException(final String message
            , final Throwable cause){
        super(message, cause);
    }
}