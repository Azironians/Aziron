package management.service.components.chainComponet;

public interface ChainComponent<Component> {

    boolean isWorking();

    Component getReplacedComponent();

    void setReplacedComponent(final Component component);
}
