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
        getInfo();

    }
    function updateProfile() {


    }

    function logout() {

    }

    function getInfo() {
        userService
            .getInfo()
            .then(
               function (response) {
                   matchInfo(response);
               }
            )

    }

    function matchInfo(user) {
        console.log(user.username);
        $usernameFld.val(user.username);
        $passwordFld.val(user.password);
        $firstNameFld.val(user.firstName);
        $lastNameFld.val(user.lastName);
        $phoneFld.val(user.phone);
        $emailFld.val(user.email);
        $roleFld.val(user.role);
        $dateOfBirthFld.val(user.dateOfBirth);
    }
})();
