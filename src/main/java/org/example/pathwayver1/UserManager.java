package org.example.pathwayver1;

/**
 * This file is where the user account management happens.
 * Console methods (registerConsole, loginConsole, etc.) have been removed
 * since the GUI controllers now handle user interaction.
 * The core logic (registration, login, lookups) remains here.
 */

import java.util.ArrayList;

public class UserManager
{
    // UserAccount attribute
    private ArrayList<UserAccount> users;

    // UserAccount constructor
    public UserManager()
    {
        users = new ArrayList<>();
    }

    // determines if the user is allowed to register with their credentials
    // and if so, the account is created
    public void registerUser(UserAccount newUser)
    {
        for (UserAccount user : users)
        {
            if (user.getUserID().equals(newUser.getUserID()))
            {
                throw new IllegalArgumentException("UserID already exists.");
            }

            if (user.getEmail().equalsIgnoreCase(newUser.getEmail()))
            {
                throw new IllegalArgumentException("Email already registered.");
            }

            if(user.getPhoneNumber().equals(newUser.getPhoneNumber()))
            {
                throw new IllegalArgumentException("Phone number already registered.");
            }
        }

        users.add(newUser);
    }

    // determines if the entry based on the user contained an existing
    // userID or password to login the application
    public UserAccount login(String id, String password)
    {
        id = id.trim().toLowerCase();
        password = password.trim();

        for (UserAccount user : users)
        {
            if (user.authenticateLogin(id, password))
            {
                return user;
            }
        }

        throw new IllegalArgumentException("Account does not exist or password incorrect.");
    }

    // ===== Lookup helpers for GUI controllers =====
    // These replace the search logic that was previously inside the console methods

    // used by RecoverIDController — finds user by email or phone
    public UserAccount findByEmailOrPhone(String value)
    {
        // normalize phone input (strip formatting chars)
        String digitsOnly = "";
        for (int i = 0; i < value.length(); i++)
        {
            char c = value.charAt(i);
            if (Character.isDigit(c))
            {
                digitsOnly += c;
            }
        }

        for (UserAccount user : users)
        {
            if (user.getEmail().equalsIgnoreCase(value))
            {
                return user;
            }

            if (!digitsOnly.isEmpty() && user.getPhoneNumber().equals(digitsOnly))
            {
                return user;
            }
        }

        return null; // no match found
    }

    // used by ResetPasswordController — finds user by email
    public UserAccount findByEmail(String email)
    {
        for (UserAccount user : users)
        {
            if (user.getEmail().equalsIgnoreCase(email))
            {
                return user;
            }
        }

        return null; // no match found
    }

    // checks if a userID is already taken (used during registration)
    public boolean isUserIDTaken(String id)
    {
        id = id.trim().toLowerCase();

        for (UserAccount user : users)
        {
            if (user.getUserID().equals(id))
            {
                return true;
            }
        }

        return false;
    }

    // checks if an email is already registered (used during registration)
    public boolean isEmailTaken(String email)
    {
        for (UserAccount user : users)
        {
            if (user.getEmail().equalsIgnoreCase(email))
            {
                return true;
            }
        }

        return false;
    }

    // checks if a phone number is already registered (used during registration)
    public boolean isPhoneTaken(String phone)
    {
        // normalize to digits only for comparison
        String digitsOnly = "";
        for (int i = 0; i < phone.length(); i++)
        {
            if (Character.isDigit(phone.charAt(i)))
            {
                digitsOnly += phone.charAt(i);
            }
        }

        for (UserAccount user : users)
        {
            if (user.getPhoneNumber().equals(digitsOnly))
            {
                return true;
            }
        }

        return false;
    }
}