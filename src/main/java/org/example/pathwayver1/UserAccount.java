package org.example.pathwayver1;

import java.util.ArrayList;

/**
 * This file is the User Account class
 * which contains the data necessary to instantiate individual User Accounts
 * This file will likely be updated in the future to consider the other program features
 * (like wallet, bank accounts, etc?) or maybe we split those functionalities in a different file
 * So far the data in this file only considers the registration and login functionalities.
 */

public class UserAccount
{
    // UserAccount Attributes/Fields //

    //=====Name fields====
    private String firstName;
    private String lastName;
    private ArrayList<Account> accounts = new ArrayList<>();
    //======================

    //===Date of Birth fields===
    private int bMonth;
    private int bDay;
    private int bYear;
    //===========================

    //===Electric Communication fields=====
    private String email;
    private String phoneNumber; //only for digits
    private String phoneNumberRaw; //only for presentation (exactly as typed)
    //============================

    //===Address fields===
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    //=====================

    //===User Credential fields====
    private String userID;
    private String password;
    //==============================


    // CONSTRUCTORS

    // UserAccount Default Constructor //
    public UserAccount()
    {

    }

    // UserAccount Overloaded Constructor //
    public UserAccount(String fN, String lM, String e,
                       String pN, String stre, String ci,
                       String sta, String zi, String co,
                       int bM, int bD, int bY,
                       String uID, String pass)
    {
        //=====Name fields====
        setFirstName(fN);
        setLastName(lM);

        //===Date of Birth fields===
        setDOB(bM, bD, bY);

        //===Electric Communication fields=====
        setEmail(e);
        setPhoneNumber(pN);

        //===Address fields===
        setStreet(stre);
        setCity(ci);
        setState(sta);
        setZipCode(zi);
        setCountry(co);

        //===User Credential fields====
        setUserID(uID);
        setPassword(pass);
    }

    // Account method

    public ArrayList<Account> getAccounts(){
        return accounts;
    }
    public void addAccount(Account account){
        accounts.add(account);
    }

    // UserAccount Get/Set methods //
    //* the getters are there in case we need them in the future

    //               NAME FIELDS

    // =======first name get/set=====
    public String getFirstName()
    {
        return firstName;
    }
    public void setFirstName(String fname)
    {
        if (fname == null || fname.trim().isEmpty())
        {
            throw new IllegalArgumentException("First name field cannot be empty.");
        }

        fname = fname.trim();

        for(int i = 0; i < fname.length(); i++)
        {
            char c = fname.charAt(i);

            if(!Character.isLetter(c) && c != '-' && c != '\'' && c != ' ')
            {
                throw new IllegalArgumentException("First name may only contain letters, spaces, hyphens, or apostrophes.");
            }
        }


        this.firstName = fname.trim();
    }
    // =====================================


    // =========last name get/set===========
    public String getLastName()
    {
        return lastName;
    }
    public void setLastName(String lname)
    {
        if (lname == null || lname.trim().isEmpty())
        {
            throw new IllegalArgumentException("Last name field cannot be empty.");
        }

        lname = lname.trim();

        for(int i = 0; i < lname.length(); i++)
        {
            char c = lname.charAt(i);

            if(!Character.isLetter(c) && c != '-' && c != '\'' && c != ' ')
            {
                throw new IllegalArgumentException("Last name may only contain letters, spaces, hyphens, or apostrophes.");
            }
        }

        this.lastName = lname.trim();
    }
    //================================

    //DOB FIELDS

    //========DOB get/set===========
    public int getBMonth()
    {
        return bMonth;
    }

    public int getBDay()
    {
        return bDay;
    }

    public int getBYear()
    {
        return bYear;
    }

    // DOB parsing helpers

    public static int parseBirthMonth(String m)
    {
        if(m == null || m.trim().isEmpty())
            throw new IllegalArgumentException("Birth month field cannot be empty.");

        if(!m.matches("-?\\d+"))
            throw new IllegalArgumentException("Month must contain digits only.");

        int month = Integer.parseInt(m);

        if(month < 1 || month > 12)
            throw new IllegalArgumentException("Month must be between 1 and 12.");

        return month;
    }

    public static int parseBirthDay(String d)
    {
        if(d == null || d.trim().isEmpty())
            throw new IllegalArgumentException("Birth day field cannot be empty.");

        if(!d.matches("-?\\d+"))
            throw new IllegalArgumentException("Day must contain digits only.");

        int day = Integer.parseInt(d);

        if(day < 1 || day > 31)
            throw new IllegalArgumentException("Day must be between 1 and 31.");

        return day;
    }

    public static int parseBirthYear(String y)
    {
        if(y == null || y.trim().isEmpty())
            throw new IllegalArgumentException("Birth year field cannot be empty.");

        if(!y.matches("\\d{4}"))
            throw new IllegalArgumentException("Year must be four digits.");

        int year = Integer.parseInt(y);

        int currentYear = java.time.LocalDate.now().getYear();

        if(year < 1900 || year > currentYear)
            throw new IllegalArgumentException("Invalid birth year.");

        return year;
    }

    public void setDOB(int mo, int da, int ye)
    {
        try
        {
            java.time.LocalDate dob = java.time.LocalDate.of(ye, mo, da);

            int age = java.time.Period.between(
                    dob,
                    java.time.LocalDate.now()
            ).getYears();

            if(age < 5)
            {
                throw new IllegalArgumentException("User must be at least 5 years old.");
            }

            this.bMonth = mo;
            this.bDay = da;
            this.bYear = ye;
        }
        catch(java.time.DateTimeException e)
        {
            throw new IllegalArgumentException("Invalid birth date.");
        }
    }

    //* this get age helper method will determine difficulty. it doesn't do much for now
    // other than contribute to telling the user if they're "Beginner" or "Advanced" difficulty.
    public int getAge()
    {
        java.time.LocalDate dob = java.time.LocalDate.of(bYear, bMonth, bDay);

        return java.time.Period.between(dob, java.time.LocalDate.now()).getYears();
    }
    //=======================================

    // ELECTRIC COMMUNICATION FIELDS

    //====== email get/set================
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String emm)
    {
        if (emm == null || emm.trim().isEmpty())
        {
            throw new IllegalArgumentException("Email field cannot be empty.");
        }

        emm = emm.trim();

        // no spaces
        if(emm.contains(" "))
        {
            throw new IllegalArgumentException("Email cannot contain spaces.");
        }

        // email must contain @
        if(!emm.contains("@"))
        {
            throw new IllegalArgumentException("Valid email must contain '@' character.");
        }

        int atIndex = emm.indexOf("@");

        // prevents user from entering "@gmail.com" as their email.
        if(atIndex == 0)
        {
            throw new IllegalArgumentException("Email must contain characters before '@'.");
        }

        // ensures that email has some domain like "gmail" "yahoo" "outlook" after the @
        if(atIndex == emm.length() - 1)
        {
            throw new IllegalArgumentException("Email must contain a domain after '@'.");
        }

        // examines chars after the "@" part
        String domain = emm.substring(atIndex + 1);

        // prevents user from typing something like "user@."
        // cant start a "." after the "@"
        if(domain.startsWith("."))
        {
            throw new IllegalArgumentException("Invalid email format.");
        }

        // prevents user from typing something like "user@gmail"
        // "." must be included after the domain (or generally just after the first char, like "g")
        if(!domain.contains("."))
        {
            throw new IllegalArgumentException("Email domain must contain a '.'");
        }

        // prevents user from typing something like "user@gmail."
        // must end in a ".com" or ".org" or just have one char atleast after the "."
        if(domain.endsWith("."))
        {
            throw new IllegalArgumentException("Invalid email format.");
        }

        // ex: the program will accept something like: "u@g.c"
        this.email = emm;
    }
    //=============================================


    //====== phone get/set==============
    public String getPhoneNumber()
    {
        return phoneNumber; // digits only
    }
    public String getPhoneNumberRaw()
    {
        return phoneNumberRaw; // exactly as typed when displayed in settings
    }
    public void setPhoneNumber(String phnum)
    {
        if (phnum == null || phnum.trim().isEmpty())
        {
            throw new IllegalArgumentException("Phone number field cannot be empty.");
        }

        phnum = phnum.trim();

        String digitsOnly = "";

        for (int i = 0; i < phnum.length(); i++)
        {
            char c = phnum.charAt(i);

            // ensure that the phone number is a valid integer(s)
            // within the 7-15 digit span
            // allows for "()", " ", "+", "-" format without rejecting
            if (Character.isDigit(c))
            {
                digitsOnly += c;
            }
            else if (c == '+' || c == '-' || c == '(' || c == ')' || c == ' ')
            {
                // allowed formatting characters, ignore but allow
            }
            else
            {
                throw new IllegalArgumentException("Phone number may only contain digits, +, -, (), or spaces.");
            }
        }

        // digit length validation
        if (digitsOnly.length() < 7 || digitsOnly.length() > 15)
        {
            throw new IllegalArgumentException("Enter a valid phone number (7–15 digits).");
        }

        // store normalized digits
        this.phoneNumber = digitsOnly;
        // store raw digits (to display for later in settings)
        this.phoneNumberRaw = phnum;
    }
    // ================================================

    //ADDRESS FIELDS

    //===== street get/set====
    public String getStreet()
    {
        return street;
    }
    public void setStreet(String stre)
    {
        if (stre == null || stre.trim().isEmpty())
        {
            throw new IllegalArgumentException("Street field cannot be empty.");
        }

        this.street = stre.trim();
    }
    // ============================


    // ===== city get/set============
    public String getCity()
    {
        return city;
    }
    public void setCity(String ci)
    {
        if (ci == null || ci.trim().isEmpty())
        {
            throw new IllegalArgumentException("City field cannot be empty.");
        }

        this.city = ci.trim();
    }
    // =================================


    // ====== state get/set============
    public String getState()
    {
        return state;
    }
    public void setState(String sta)
    {
        if (sta == null || sta.trim().isEmpty())
        {
            throw new IllegalArgumentException("State field cannot be empty.");
        }

        this.state = sta.trim();
    }
    // =================================


    // ====zip code get/set==========
    public String getZipCode()
    {
        return zipCode;
    }
    public void setZipCode(String zi)
    {
        if (zi == null || zi.trim().isEmpty())
        {
            throw new IllegalArgumentException("Zip code field cannot be empty.");
        }

        // zip code only allows for digits
        for (int i = 0; i < zi.length(); i++)
        {
            if (!Character.isDigit(zi.charAt(i)))
            {
                throw new IllegalArgumentException("Zip code must contain digits only.");
            }
        }

        this.zipCode = zi.trim();
    }
    //===============


    //======country get/set=======
    public String getCountry()
    {
        return country;
    }
    public void setCountry(String co)
    {
        if (co == null || co.trim().isEmpty())
        {
            throw new IllegalArgumentException("Country field cannot be empty.");
        }

        this.country = co.trim();
    }
    // ================================

    // USER CREDENTIAL FIELDS

    // ====== userID get/set========
    public String getUserID()
    {
        return userID;
    }
    public void setUserID(String id)
    {
        if (id == null || id.trim().isEmpty())
        {
            throw new IllegalArgumentException("User ID field cannot be empty.");
        }

        id = id.trim().toLowerCase();

        // valid length
        if (id.length() < 6 || id.length() > 15)
        {
            throw new IllegalArgumentException("User ID must be 6-15 characters.");
        }

        // no spaces
        if (id.contains(" "))
        {
            throw new IllegalArgumentException("User ID cannot contain spaces.");
        }

        // must start with letter
        if (!Character.isLetter(id.charAt(0)))
        {
            throw new IllegalArgumentException("User ID must start with a letter.");
        }

        // no leading or trailing underscore
        if (id.startsWith("_") || id.endsWith("_"))
        {
            throw new IllegalArgumentException("User ID cannot start or end with underscore.");
        }

        // only letters, numbers, underscore
        for (int i = 0; i < id.length(); i++)
        {
            char c = id.charAt(i);

            if (!Character.isLetterOrDigit(c) && c != '_')
            {
                throw new IllegalArgumentException("User ID may contain only letters, numbers, or underscore.");
            }
        }

        this.userID = id;
    }
    // ===================================


    // ====== password get/set ===============
    public String getPassword()
    {
        return password;
    }
    public void setPassword(String pass)
    {
        if (pass == null || pass.trim().isEmpty())
        {
            throw new IllegalArgumentException("Password field cannot be empty.");
        }

        pass = pass.trim();

        // valid length
        if (pass.length() < 8 || pass.length() > 30)
        {
            throw new IllegalArgumentException("Password must be between 8 and 30 characters.");
        }

        // no spaces
        if (pass.contains(" "))
        {
            throw new IllegalArgumentException("Password cannot contain spaces.");
        }

        // password cannot be the same as userID
        if (userID != null && pass.equalsIgnoreCase(userID))
        {
            throw new IllegalArgumentException("Password cannot be the same as the UserID.");
        }

        boolean hasLetter = false;
        boolean hasNumber = false;
        boolean hasSpecialChar = false;

        // validate password by examination of each char
        for (int i = 0; i < pass.length(); i++)
        {
            char c = pass.charAt(i);

            if (Character.isLetter(c))
            {
                hasLetter = true;
            }
            else if (Character.isDigit(c))
            {
                hasNumber = true;
            }
            else
            {
                hasSpecialChar = true;
            }
        }

        if (!hasLetter || !hasNumber || !hasSpecialChar)
        {
            throw new IllegalArgumentException("Password must contain at least one letter, \none number, and one special character.");
        }

        this.password = pass;
    }
    // ============================================END OF GET/SETTERS=========================

    // ADDITIONAL METHODS //

    // checks to see if user logged in with a valid registered account
    public boolean authenticateLogin(String enteredID, String enteredPassword)
    {
        return userID.equals(enteredID.toLowerCase()) && password.equals(enteredPassword);
    }

    // sets difficulty level. for now this only tells the user what difficulty
    // they're on in terms of learning content as soon as they login.
    // this will either be expanded here or in a different file.
    public String getDifficultyLevel()
    {
        int age = getAge();

        if(age < 12)
        {
            return "Child-mode";
        }
        else
        {
            return "Full-Access";
        }
    }

    // helper method for reset password option to verify bday
    public boolean verifyDOB(int month, int day, int year)
    {
        return bMonth == month && bDay == day && bYear == year;
    }

}