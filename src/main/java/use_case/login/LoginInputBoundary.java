package use_case.login;

/**
 * Input Boundary for actions which are related to logging in.
 */
public interface LoginInputBoundary {

    /**
     * Executes the login use case.
     *
     * @param loginInputData the input data
     */
    void execute(LoginInputData loginInputData);

    /**
     * Switches to the signup view.
     */
    void switchToSignupView();

    /**
     * Switches to the instructions view.
     */
    void switchToInstructionsView();
}
