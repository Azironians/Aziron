package management.pipeline;

import management.actionManagement.actions.ActionEvent;

import java.util.List;

public interface APipeNode {

    ActionEvent handleEvent(final ActionEvent actionEvent);

    List<APipe> getInnerPipes();
}