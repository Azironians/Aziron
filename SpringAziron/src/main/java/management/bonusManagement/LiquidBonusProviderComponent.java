package management.bonusManagement;

import management.service.components.providerComponent.ProviderComponent;

public interface LiquidBonusProviderComponent extends ProviderComponent<Integer> {

    ProviderComponent<Integer> getReplacedProviderComponent();
}
