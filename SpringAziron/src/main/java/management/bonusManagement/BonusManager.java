package management.bonusManagement;

import management.bonusManagement.service.components.DefaultBonusProviderComponent;
import management.service.components.chainComponet.ChainComponent;
import management.service.components.providerComponent.ProviderComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public final class BonusManager {

    private static final int START_DECK_SIZE = 16;
    
    private ProviderComponent<Integer> firstProviderComponent;

    private ProviderComponent<Integer> secondProviderComponent;

    private ProviderComponent<Integer> thirdProviderComponent;

    private final List<ProviderComponent<Integer>> providerComponentList;

    {
        this.firstProviderComponent = new DefaultBonusProviderComponent(1, START_DECK_SIZE);
        this.secondProviderComponent = new DefaultBonusProviderComponent(2, START_DECK_SIZE);
        this.thirdProviderComponent = new DefaultBonusProviderComponent(3, START_DECK_SIZE);
        this.providerComponentList = new ArrayList<>(){{
            addAll(Arrays.asList(firstProviderComponent, secondProviderComponent, thirdProviderComponent));
        }};
        System.out.println("INSTALLED!!!");
    }

    public final void setDefaultProviderComponent(final int index, final int deckSize){
        final ProviderComponent<Integer> providerComponent = this.providerComponentList.get(index);
        final int priority = providerComponent.getPriority();
        final ProviderComponent<Integer> newProviderComponent = getDefaultProviderComponent(priority, deckSize);
        this.providerComponentList.set(index, newProviderComponent);
    }

    public final void returnPreviousProviderComponent(final int index, final int deckSize
            , final ProviderComponent<Integer> providerComponent){
        providerComponent.setPriority(this.providerComponentList.get(index).getPriority());
        final Class providerComponentClazz = providerComponent.getClass();
        final Class[] interfaces = providerComponentClazz.getInterfaces();
        for (final Class clazz : interfaces){
            if (clazz == ChainComponent.class){
                final ChainComponent<ProviderComponent<Integer>> chainComponent = (ChainComponent) providerComponent;
                final ProviderComponent<Integer> replacedProviderComponent = chainComponent.getReplacedComponent();
                if (!chainComponent.isWorking()){
                    this.returnPreviousProviderComponent(index, deckSize, replacedProviderComponent);
                    return;
                }
            }
            if (clazz == DefaultBonusProviderComponent.class){
                final DefaultBonusProviderComponent defaultProvider = (DefaultBonusProviderComponent) providerComponent;
                defaultProvider.setDeckSize(deckSize);
            }
        }
        this.providerComponentList.set(index, providerComponent);
    }

    public final void setCustomProviderComponent(final int index, final ProviderComponent<Integer> providerComponent){
        providerComponent.setPriority(this.providerComponentList.get(index).getPriority() + 3);
        this.providerComponentList.set(index, providerComponent);
    }

    public final int getAvailableProviderComponent(){
        int index  = 0;
        int i = 0;
        ProviderComponent<Integer> minPriorityProviderComponent = providerComponentList.get(0);
        for (final ProviderComponent<Integer> providerComponent: providerComponentList){
            if (providerComponent.getPriority() <= minPriorityProviderComponent.getPriority()){
                minPriorityProviderComponent = providerComponent;
                index = i;
            }
            i++;
        }
        return index;
    }

    //Interesting getter:
    public final List<ProviderComponent<Integer>> getProviderComponentList() {
        for (final ProviderComponent<Integer> providerComponent :providerComponentList){
            providerComponent.setPriority(providerComponent.getPriority() + 1);
        }
        return this.providerComponentList;
    }

    private ProviderComponent<Integer> getDefaultProviderComponent(final int inputPriority, final int inputDeckSize){
        return new DefaultBonusProviderComponent(inputPriority, inputDeckSize);
    }
}