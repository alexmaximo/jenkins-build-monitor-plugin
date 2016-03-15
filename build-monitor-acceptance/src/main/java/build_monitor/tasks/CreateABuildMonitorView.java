package build_monitor.tasks;

import build_monitor.tasks.configuration.SaveTheChangesToBuildMonitor;
import build_monitor.tasks.configuration.TodoList;
import core_jenkins.user_interface.JenkinsHomePage;
import core_jenkins.user_interface.NewViewPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.thucydides.core.annotations.Step;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static org.openqa.selenium.Keys.ENTER;

public class CreateABuildMonitorView implements Task {
    public static CreateABuildMonitorView called(String name) {
        return instrumented(CreateABuildMonitorView.class, name);
    }

    public Task andConfigureItTo(Task configurationTask) {
        this.configureBuildMonitor.add(configurationTask);

        return this;
    }

    @Override
    @Step("{0} creates a 'Build Monitor View' called '#buildMonitorName'")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(JenkinsHomePage.New_View_link),
                Click.on(NewViewPage.Build_Monitor_View),
                Enter.theValue(buildMonitorName).into(NewViewPage.View_Name).thenHit(ENTER),
                configureBuildMonitor,
                SaveTheChangesToBuildMonitor.andExitTheConfigurationScreen()
        );
    }

    public CreateABuildMonitorView(String name) {
        this.buildMonitorName = name;
    }

    private final String   buildMonitorName;
    private final TodoList configureBuildMonitor = TodoList.empty();
}
