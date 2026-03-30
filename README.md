# Pathway-Banking-GUI-SWE-3313-

Login/Enrollment section of the project complete
this includes the bullets included from the development reference document

## Login/Enrollment Screen Page: 

Start up/ Opening the application first:  
- Contains login fields (userID + password),  
- enrollment/registration button 
- Forgot userID button/”link” 
- Reset password button/”link” 
- Exit application button – close out the application (or the user can manually press the close button) 

Registration form: 
- New user sign-up form when user presses registration button 
- Fields: first name, last name, email, phone number, address (street, city, zip, state, country), date of birth, userID, password. 
- Validate all fields, checks for duplicated, enforce formatting rules 

Credential Recovery - userID: 
- User clicks Forgot userID button/”link” 
- User enters registered email or phone number. System looks up and displays their userID 

Credential Recovery – Password Reset: 
- User enters email 
- Verifies date of birth 
- Enters new password + confirmation. Validates password rules 

## Login/Enrollment Function:

User registration / enrollment:  

allow first time users to create a User Account with mock credentials 

- Build registration form UI with fields: first name, last name, email, phone, address, DOB, userID, password 
- Implement field validation (formatting rules, required fields, character restrictions) 
- Check for duplicate email, phone number, and userID against existing accounts 
- Store new User Account to local file system upon successful registration 
- Auto-assign a default Debit/Checking Account to new users 
- Show confirmation on success and redirect to login page 
- Allow user to cancel registration at any point (discard incomplete data) 
- Display specific error messages for each invalid or duplicate field 

Login System: 

Validate credentials and load user sessions with saved state 

- Build login form UI with userID and password fields 
- Mask password characters during input 
- Validate entered credentials against stored User Account data 
- TODO On success: display Dashboard 
- On failure: show error message that does NOT reveal which credential was wrong 
- 5 attempts to login before pausing attempts for 30 seconds
- Never auto-fill credentials after logout — fields must be empty every time 
- Provide links to Recover UserID and Reset Password flows 
- Provide an Exit Application button on login screen 

 

Recover userID: 

Retrieve userID by verifying registered email or phone number 

- Look up matching account and display the associated userID on screen 
- Show error if no matching account is found 
- Allow cancel to return to login page without showing any data 

 

Reset Passowrd: 

Allow password reset after verifying email + dob 

- Step 1: User enters registered email → look up account 
- Step 2: Prompt for date of birth verification → compare to stored record 
- Step 3: Show new password form (enter + confirm) → validate against password rules 
- Reject if passwords don't match or fail validation rules 
- Update stored password and redirect to login page 
- Show specific error messages for each failure scenario 
- Allow cancel at any step (discard changes, return to login) 

Exit App:

user exits out application


