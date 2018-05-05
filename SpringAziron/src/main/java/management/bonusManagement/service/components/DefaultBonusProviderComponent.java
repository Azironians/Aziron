package management.bonusManagement.service.components;

import management.service.components.providerComponent.ProviderComponent;

import java.util.Random;

public final class DefaultBonusProviderComponent implements ProviderComponent<Integer> {

    private int priority;

    private int deckSize;

    private final Random random = new Random();

    public DefaultBonusProviderComponent(final int priority, final int deckSize){
        this.priority = priority;
        this.deckSize = deckSize;
    }

    @Override
    public final Integer getValue() {
        return this.random.nextInt(this.deckSize);
    }

    @Override
    public final int getPriority() {
        return priority;
    }

    @Override
    public final void setPriority(final int priority) {
        this.priority = priority;
    }

    public final void setDeckSize(final int deckSize) {
        this.deckSize = deckSize;
    }
}