package main;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import gui.sceneMover.SceneMover;
import gui.windows.AWindow;
import gui.windows.WindowType;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import modules.MainModule;
import modules.gameModule.AGameModule;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.URL;
import java.util.EnumMap;
import java.util.function.Function;

public final class AGame extends Application {

    @Inject
    @Named("icon path")
    private static String iconPath;

    @Inject
    @Named("build number")
    private static String buildNumber;

    private final Injector injector = Guice.createInjector(new MainModule(), new AGameModule(this));

    private ClassPathXmlApplicationContext context;

    @Inject
    private Stage stage;

    private EnumMap<WindowType, AWindow> windowMap;

    @Inject
    private SceneMover sceneMover;

    {
        final Function<URL, FXMLLoader> makeFXMLLoader = url -> {
            final FXMLLoader result = new FXMLLoader(url);
            result.setControllerFactory(injector::getInstance);
            return result;
        };

        this.windowMap = new EnumMap<>(WindowType.class) {{
            put(WindowType.INITIALIZATION, new AWindow(makeFXMLLoader.apply(WindowType.INITIALIZATION.URL())));
            put(WindowType.MENU, new AWindow(makeFXMLLoader.apply(WindowType.MENU.URL())));
            put(WindowType.AUTHORIZATION, new AWindow(makeFXMLLoader.apply(WindowType.AUTHORIZATION.URL())));
            put(WindowType.PROFILE, new AWindow(makeFXMLLoader.apply(WindowType.PROFILE.URL())));
            put(WindowType.CHOICE_BONUS, new AWindow(makeFXMLLoader.apply(WindowType.CHOICE_BONUS.URL())));
            put(WindowType.FAST_CHOICE_HERO, new AWindow(makeFXMLLoader.apply(WindowType.FAST_CHOICE_HERO.URL())));
            put(WindowType.MATCHMAKING, new AWindow(makeFXMLLoader.apply(WindowType.MATCHMAKING.URL())));
        }};
    }

    private void stageInitialization() {
        this.stage.setOnCloseRequest(event -> System.exit(0));
        this.stage.getIcons().add(new Image(iconPath));
        this.stage.setResizable(false);
        this.stage.setTitle(buildNumber);
        this.stage.show();
    }

    private void createContext(){
        this.context = new ClassPathXmlApplicationContext("spring/context/AzironMainContext.xml");
    }

    @Override
    public final void start(Stage virtualStage) {
        this.createContext();
        this.stageInitialization();
        this.sceneMover.moveToScene(WindowType.INITIALIZATION);
    }

    public static void main(final String[] args) {
        launch(args);
    }

    public final EnumMap<WindowType, AWindow> getWindowMap() {
        return this.windowMap;
    }

    public final ClassPathXmlApplicationContext getContext() {
        return this.context;
    }
}