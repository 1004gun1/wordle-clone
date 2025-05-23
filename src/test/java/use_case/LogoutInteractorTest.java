package use_case;

import data_access.repository.UserRepositoryImpl;
import interface_adapter.ViewManagerModel;
import interface_adapter.gameend.GameEndViewModel;
import interface_adapter.grid.GridViewModel;
import interface_adapter.instructions.InstructionsViewModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.security.PasswordHasher;
import org.junit.jupiter.api.Test;
import use_case.logout.*;
import use_case.service.UserService;

import static org.junit.jupiter.api.Assertions.*;

class LogoutInteractorTest {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    UserRepositoryImpl userRepository = new UserRepositoryImpl();
    PasswordHasher passwordHasher = new PasswordHasher();
    UserService userService = new UserService(userRepository, passwordHasher);

    @Test
    void successTest() {
        LogoutInputData inputData = new LogoutInputData(USERNAME);

        // For the success test, we need to add user to the data access repository before we log in.
        userService.registerUser(USERNAME, PASSWORD);
        userService.setCurrentUsername(USERNAME);

        // This creates a successPresenter that tests whether the test case is as we expect.
        LogoutOutputBoundary successPresenter = new LogoutOutputBoundary() {
            @Override
            public void prepareSuccessView(LogoutOutputData user) {
                // check that the output data contains the username of who logged out
                assertEquals(USERNAME, user.getUsername());
            }

            @Override
            public void switchToInstructionView() {
                return;
            }

        };

        LogoutInputBoundary interactor = new LogoutInteractor(userService, successPresenter);
        interactor.execute(inputData);

        // check that the user was logged out
        assertNull(userService.getCurrentUsername());
    }

    @Test
    void switchToInstructionView() {
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        LoginViewModel loginViewModel = new LoginViewModel();
        InstructionsViewModel instructionsViewModel = new InstructionsViewModel();
        GameEndViewModel gameEndViewModel = new GameEndViewModel();
        GridViewModel gridViewModel = new GridViewModel();
        LogoutOutputBoundary logoutPresenter = new LogoutPresenter(viewManagerModel, loginViewModel, instructionsViewModel,
                gameEndViewModel, gridViewModel);
        LogoutInputBoundary interactor = new LogoutInteractor(userService, logoutPresenter);

        interactor.switchToInstructionView();

        assertEquals("instructions", viewManagerModel.getState());
    }

}