(function() {
    var $usernameFld, $passwordFld;
    var $loginBtn;
    var userService = new UserServiceClient();
    $(main);

    function main() {
        $usernameFld = $('#username');
        $passwordFld = $('#password');
        $loginBtn = $('#signinBtn');
        $loginBtn.click(login);
    }
    function login() {
        var user = {
            username : $usernameFld.val(),
            password : $passwordFld.val()
        }

        console.log(user);
        userService
            .login(user)
            .then(
                function (response) {
                    if(response.ok){
                        window.location.href = "../profile/profile.template.client.html";
                    } else {
                        alert('Login failed');
                    }
            })

    }
})();
