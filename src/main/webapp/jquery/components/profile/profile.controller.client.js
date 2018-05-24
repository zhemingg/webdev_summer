(function() {
    var $usernameFld, $passwordFld, $firstNameFld, $lastNameFld, $phoneFld, $emailFld, $roleFld, $dateOfBirthFld;
    var $updateBtn, $logoutBtn;
    var userService = new UserServiceClient();
    $(main);

    function main() {
        $usernameFld = $('#username');
        $passwordFld = $('#password');
        $firstNameFld = $('#firstName');
        $lastNameFld = $('#lastName');
        $phoneFld = $('#phone');
        $emailFld = $('#email');
        $roleFld = $('#role');
        $dateOfBirthFld = $('#dateOfBirth');
        $updateBtn = $('#updateBtn');
        $updateBtn.click(updateProfile);
        $logoutBtn = $('#logoutBtn');
        $logoutBtn.click(logout);
    }
    function updateProfile() {
        

    }

    function logout() {

    }
})();
