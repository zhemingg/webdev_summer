(function () {

    var $usernameFld, $passwordFld;
    var $removeBtn, $editBtn, $createBtn, $updateBtn;
    var $firstNameFld, $lastNameFld;
    var $userRowTemplate, $tbody;
    var $roleFld, $userId
    var userService = new UserServiceClient();

    $(main);

    /*
     executes on document load, when the browser is done parsing the html page and the dom is ready.
     Retrieved the dom elements needed later in the controller such as the form elements, action icons, and templates.
     Binds action icons, such as create, update, select, and delete, to respective event handlers
    */
    function main() {
        $userRowTemplate = $('.wbdv-template');
        $tbody = $('.wbdv-tbody');
        $createBtn = $('.wbdv-create');
        $createBtn.click(createUser);
        $updateBtn = $('.wbdv-update');
        $updateBtn.click(updateUser);
        findAllUsers();
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
        $roleFld = $('#roleFld').val();

        var user = new User($usernameFld, $passwordFld, $firstNameFld, $lastNameFld, $roleFld, null, null, null);
        userService
            .createUser(user)
            .then(findAllUsers);



    }

    /*
    called whenever the list of users needs to be refreshed.
    Uses user service findAllUsers() to retrieve all the users and passes response to renderUsers
    */
    function findAllUsers() {
        userService
            .findAllUsers()
            .then(renderUsers);
    }

    /*called whenever a particular user needs to be retrieved by their id, as in when a user is selected for editing.
    Reads the user is from the icon id attribute.
    Uses user service findUserById() to retrieve user and then updates the form on server response
    */
    function findUserById() {
        return userService.findUserById($userId);
    }


    /*
    handles delete user event when user clicks the cross icon.
    Reads the user is from the icon id attribute. Uses user service deleteUser() to send a delete request to the server.
    Updates user list on server response
    */
    function deleteUser() {
        $removeBtn = $(event.currentTarget);
        $userId = $removeBtn
            .parent()
            .parent()
            .parent()
            .attr('id');
        if($userId !== -1) {
            userService
                .deleteUser($userId)
                .then(findAllUsers);
        }
        $userId = -1;

    }




    /*
    handles update user event when user clicks on check mark icon.
    Reads the user is from the icon id attribute.
     Reads new user values form the form, creates a user object and then uses user service updateUser() to send the new user data to server.
     Updates user list on server response
    */
    function updateUser() {
        if ($userId !== -1){
            var user = new User($('#usernameFld').val(),
                    $('#passwordFld').val(),
                    $('#firstNameFld').val(),
                    $('#lastNameFld').val(),
                    $('#roleFld').val(),
                    null, null, null);

            userService.updateUser($userId, user).then(findAllUsers);
            console.log("updated successfully");
        } else {
            console.log("Can not update information");
        }
        $userId = -1;
    }

    /*
    accepts a user object as parameter and updates the form with the user properties
    */
    function renderUser(user) {
        $('#usernameFld').val(user.username);
        $('#passwordFld').val(user.password);
        $('#firstNameFld').val(user.firstName);
        $('#lastNameFld').val(user.lastName);
        $('#roleFld').val(user.role);
    }

    /*
    Select the user when click the edit icon
    */
    function selectUser() {
        $editBtn = $(event.currentTarget);
        $userId = $editBtn
            .parent()
            .parent()
            .parent()
            .attr('id');
        findUserById().then(renderUser);
    }

    /*
    accepts an array of user instances, clears the current content of the table body, iterates over the array of users,
    clones a table row template for each user instance, populates the table row with the user object properties, adds the table row to the table body
    */
    function renderUsers(users) {
        $tbody.empty();
        for (var i = 0; i < users.length; i++) {

            var user = users[i];
            var clone = $userRowTemplate.clone();
            clone.attr('id', user.id);
            $removeBtn = clone.find('#wbdv-remove');
            $removeBtn.click(deleteUser);
            $editBtn = clone.find('#wbdv-edit');
            $editBtn.click(selectUser);
            clone.find('.wbdv-username')
                .html(user.username);
            clone.find('.wbdv-first-name')
                .html(user.firstName);
            clone.find('.wbdv-last-name')
                .html(user.lastName);
            clone.find('.wbdv-role')
                .html(user.role);
            $tbody.append(clone);
        }

    }
})();
