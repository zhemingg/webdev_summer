(function() {
    var $usernameFld, $passwordFld, $firstNameFld, $lastNameFld, $phoneFld, $emailFld, $roleFld, $dateOfBirthFld;
    var $updateBtn, $logoutBtn;
    var user = null;
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
        console.log($phoneFld.val());
        $updateBtn = $('#updateBtn');
        $updateBtn.click(updateProfile);
        $logoutBtn = $('#logoutBtn');
        $logoutBtn.click(logout);
        getInfo();

    }

    function updateProfile() {
        console.log(user);
        if (!user){
            $('.alert-danger').css('display', 'block');
            return;
        } else {
            user = new User($usernameFld.val(), $passwordFld.val(), $firstNameFld.val(), $lastNameFld.val(),
                $roleFld.val(), $emailFld.val(), $phoneFld.val(), $dateOfBirthFld.val());
            //console.log(user.phone);
            userService
                .updateProfile(user)
                .then(function (response) {

                        matchInfo(response);
                        $('.alert-success').css('display', 'block')


                })
        }

    }

    function logout() {
        user = null;
        userService.logout();
        $('.alert-danger').css('display', 'block');
        window.location.href = "../login/login.template.client.html";
        console.log(user);


    }

    function getInfo() {
        userService
            .getInfo()
            .then(
               function (response) {
                   user = response;
                   matchInfo(user);
               }
            )

    }

    function matchInfo(user) {
            $usernameFld.val(user.username);
            $passwordFld.val(user.password);
            $firstNameFld.val(user.firstName);
            $lastNameFld.val(user.lastName);
            $phoneFld.val(user.phone);
            $emailFld.val(user.email);
            $roleFld.val(user.role);
            var date;
            if (user.dateOfBirth !== null) {
                date = user.dateOfBirth.split('T')[0];
            }
            $dateOfBirthFld.val(date);
    }
})();
