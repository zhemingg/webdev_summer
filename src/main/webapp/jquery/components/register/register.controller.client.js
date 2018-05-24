
(function () {
    var $usernameFld, $passwordFld, $verifyPasswordFld;
    var $registerBtn;
    var userService = new UserServiceClient();
    $(main);

    function main() {
        $usernameFld = $('#usernameFld');
        $passwordFld = $('#passwordFld');
        $verifyPasswordFld = $('#verifyPasswordFld');
        $registerBtn = $('#register');
        $registerBtn.click(register);
    }
    function register() {

        if ($passwordFld.val() !== $verifyPasswordFld.val()){
            alert("Password do not match");
        } else {
            var user = {
                username: $usernameFld.val(),
                password : $passwordFld.val()
            }

            userService.register(user).then(function (response) {
                if (response.ok){
                    alert("Registered Successfully");
                } else {
                    alert("Sorry, The username have been taken. Please try a new username");
                }
            })
        }

    }
})();
