package org.hbrs.se2.project.collhbrs.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.hbrs.se2.project.collhbrs.control.LoginControl;
import org.hbrs.se2.project.collhbrs.control.exception.DatabaseUserException;
import org.hbrs.se2.project.collhbrs.dtos.UserDTO;
import org.hbrs.se2.project.collhbrs.util.Globals;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * View zur Darstellung der Startseite. Diese zeigt dem Benutzer ein Login-Formular an.
 **/


@Route(value = "" )
@RouteAlias(value = "login")
@PageTitle("Login")
public class MainView extends VerticalLayout {

    @Autowired
    private LoginControl loginControl;

    private Button register = new Button("Registrieren");

    public MainView() {
        setSizeFull();
        add(createTitle());

        register.addClickListener(e-> {
            navigateToRegistrationPage();});

        LoginForm component = new LoginForm();
        component.addLoginListener(e -> {

            boolean isAuthenticated = false;
            try {
                isAuthenticated = loginControl.authentificate( e.getUsername() , e.getPassword() );

            } catch (DatabaseUserException databaseException) {
                Dialog dialog = new Dialog();
                dialog.add( new Text( databaseException.getReason()) );
                dialog.setWidth("400px");
                dialog.setHeight("150px");
                dialog.open();
            }
            if (isAuthenticated) {
                grabAndSetUserIntoSession();
                navigateToMainPage();

            } else {
                component.setError(true);
            }
        });

        add(component);
        add(createButtonLayout());
        this.setAlignItems( Alignment.CENTER );
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        register.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(register);
        return buttonLayout;
    }

    private Component createTitle() {
        VerticalLayout layout = new VerticalLayout();
        HorizontalLayout logoLayout = new HorizontalLayout();
        // Hinzufügen des Logos
        logoLayout.setId("logo");
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        logoLayout.add(new Image("images/logo_53Prog_100px.png", "53Programming logo"));
        logoLayout.add(new H1("Coll@Hbrs Platform"));
        // Hinzufügen des Menus inklusive der Tabs

        return logoLayout;

    }

    private void grabAndSetUserIntoSession() {
        UserDTO userDTO = loginControl.getCurrentUser();
        UI.getCurrent().getSession().setAttribute( Globals.CURRENT_USER , userDTO );
    }


    private void navigateToMainPage() {
        // Navigation zur Startseite, hier die jeweilige Profilseite, die noch eingebunden werden muss
        UI.getCurrent().navigate(Globals.Pages.APP_VIEW);
    }

    private void navigateToRegistrationPage() {
        // Navigation zur Registierungsseite
        UI.getCurrent().navigate(Globals.Pages.REGISTRATION_VIEW);
    }
}

