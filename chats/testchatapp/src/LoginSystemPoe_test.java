import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginSystemPoe_test {
    private LoginSystemPOE logSystem = new LoginSystemPOE();

    @Test
    public void CheckUsernameFormat() {
        // Correct format of the username
        assertTrue(logSystem.checkUsernameFormat("kyl_1"));
        assertTrue(logSystem.checkUsernameFormat("a_b"));

        // Incorrect format of the username
        assertFalse(logSystem.checkUsernameFormat("kyle!!!!!!!"));
        assertFalse(logSystem.checkUsernameFormat("kyle"));
        assertFalse(logSystem.checkUsernameFormat("kyl1_1"));
    }

    @Test
    public void CheckPasswordComplexity() {
        // Correct passwords
        assertTrue(logSystem.checkPasswordComplexity("Ch&&sec@ke99!"));
        assertTrue(logSystem.checkPasswordComplexity("A1@bcdefg"));

        // Incorrect passwords
        assertFalse(logSystem.checkPasswordComplexity("password"));  // No caps, numbers, special chars
        assertFalse(logSystem.checkPasswordComplexity("Password"));  // No numbers or special chars
        assertFalse(logSystem.checkPasswordComplexity("P@ssw0r"));   // Too short
        assertFalse(logSystem.checkPasswordComplexity("password123")); // No caps or special chars
    }

    @Test
    public void checkCellPhoneNumberFormat() {
        // Correct formats of the phone number
        assertTrue(logSystem.checkCellPhoneNumberFormat("+27831234567"));
        assertTrue(logSystem.checkCellPhoneNumberFormat("+1234567890"));

        // Incorrect formats of the phone number
        assertFalse(logSystem.checkCellPhoneNumberFormat("08966553"));     // No international code
        assertFalse(logSystem.checkCellPhoneNumberFormat("+123456"));      // Too short
        assertFalse(logSystem.checkCellPhoneNumberFormat("27831234567"));  // Missing +
        assertFalse(logSystem.checkCellPhoneNumberFormat("+abc1234567"));  // Contains letters
    }

    @Test
    public void testAuthenticateUser() {
        // Set up test data using reflection since fields are private
        setPrivateField(logSystem, "userName", "test_1");
        setPrivateField(logSystem, "password", "P@ssw0rd123");

        // Successful login
        assertTrue(logSystem.authenticateUser("test_1", "P@ssw0rd123"));

        // Failed logins
        assertFalse(logSystem.authenticateUser("wrong", "P@ssw0rd123"));
        assertFalse(logSystem.authenticateUser("test_1", "wrong"));
        assertFalse(logSystem.authenticateUser("wrong", "wrong"));
    }

    @Test
    public void testGetLoginStatusMessage() {
        setPrivateField(logSystem, "firstName", "Kyle");
        setPrivateField(logSystem, "lastName", "");

        // Successful login message
        assertEquals("Welcome Kyle, it is great to see you again.",
                logSystem.getLoginStatusMessage(true));

        // Failed login message
        assertEquals("Username or password incorrect, please try again.",
                logSystem.getLoginStatusMessage(false));
    }

    @Test
    public void testRegisterNewUser() {
        // Simulate user input
        String simulatedInput = "Kyle\ntest_1\nP@ssw0rd123!\n+27831234567\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner testScanner = new Scanner(inputStream);

        String result = logSystem.registerNewUser(testScanner);

        assertEquals("Registration successful!", result);

        // Verify the fields were set correctly
        assertEquals("test_1", getPrivateField(logSystem, "userName"));
        assertEquals("P@ssw0rd123!", getPrivateField(logSystem, "password"));
        assertEquals("+27831234567", getPrivateField(logSystem, "cellNumber"));
        assertEquals("John", getPrivateField(logSystem, "firstName"));
        assertEquals("", getPrivateField(logSystem, "lastName"));
    }

    // Helper method to set private fields using reflection
    private void setPrivateField(Object object, String fieldName, Object value) {
        try {
            java.lang.reflect.Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (Exception e) {
            throw new RuntimeException("Error setting field: " + fieldName, e);
        }
    }

    // Helper method to get private fields using reflection
    private Object getPrivateField(Object object, String fieldName) {
        try {
            java.lang.reflect.Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception e) {
            throw new RuntimeException("Error getting field: " + fieldName, e);
        }
    }
}

