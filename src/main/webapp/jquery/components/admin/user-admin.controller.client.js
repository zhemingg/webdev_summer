(function () {
    var $usernameFld, $passwordFld;
    var $removeBtn, $editBtn, $createBtn;
    var $firstNameFld, $lastNameFld;
    var $userRowTemplate, $tbody;
    var $emailFld, $phoneFld, $dateOfBirthFld;
    var userService = new AdminUserServiceClient();
    $(main);

    /*
     executes on document load, when the browser is done parsing the html page and the dom is ready.
     Retrieved the dom elements needed later in the controller such as the form elements, action icons, and templates.
     Binds action icons, such as create, update, select, and delete, to respective event handlers
    */
    function main() {

    }

    /*
    handles create user event when user clicks on plus icon. Reads from the form elements and creates a user object.
    Uses the user service createUser() function to create the new user. Updates the list of users on server response
    */
    function createUser() {
        $usernameFld = $('#usernameFld').val();
        $passwordFld = $('#passwordFld').val();
        $firstNameFld = $('#firstNameFld').val();
        $lastNameFld = $('#lastNameFld').val();
        $emailFld = $('#emailFld').val();
        $phoneFld = $('#phoneFld').val();
        $dateOfBirthFld = $('#dateOfBirthFld');

        var user = {
            username : $usernameFld,
            password : $passwordFld,
            firstName : $firstNameFld,
            lastName : $lastNameFld,
            email : $emailFld,
            phone : $phoneFld,
            dateOfBirth : $dateOfBirthFld
        }

        userService
            .createUser(user)
            .then(findAllUsers);

    }

    /*
    called whenever the list of users needs to be refreshed.
    Uses user service findAllUsers() to retrieve all the users and passes response to renderUsers
    */
    function findAllUsers() {

    }

    /*called whenever a particular user needs to be retrieved by their id, as in when a user is selected for editing.
    Reads the user is from the icon id attribute.
    Uses user service findUserById() to retrieve user and then updates the form on server response
    */
    function findUserById() {

    }


    /*
    handles delete user event when user clicks the cross icon.
    Reads the user is from the icon id attribute. Uses user service deleteUser() to send a delete request to the server.
    Updates user list on server response
    */
    function deleteUser() {

    }


    function selectUser() {

    }

    /*
    handles update user event when user clicks on check mark icon.
    Reads the user is from the icon id attribute.
     Reads new user values form the form, creates a user object and then uses user service updateUser() to send the new user data to server.
     Updates user list on server response
    */
    function updateUser() {

    }

    /*
    accepts a user object as parameter and updates the form with the user properties
    */
    function renderUser(user) {

    }

    /*
    accepts an array of user instances, clears the current content of the table body, iterates over the array of users,
    clones a table row template for each user instance, populates the table row with the user object properties, adds the table row to the table body
    */
    function renderUsers(users) {

    }
})();