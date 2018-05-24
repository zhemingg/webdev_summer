
function UserServiceClient() {
    this.createUser = createUser;
    this.findAllUsers = findAllUsers;
    this.findUserById = findUserById;
    this.deleteUser = deleteUser;
    this.updateUser = updateUser;
    this.register = register;
    this.login = login;
    this.getInfo = getInfo;
    this.url = 'http://localhost:8080/api/user';
    this.registerUrl = 'http://localhost:8080/api/register';
    this.loginUrl = 'http://localhost:8080/api/login';
    this.profile = 'http://localhost:8080/api/profile'
    var self = this;


    /*accepts a user object and POSTs it to a user Web service. Receives status*/
    function createUser(user) {
        return fetch(self.url, {
            method: 'post',
            body: JSON.stringify(user),
            headers: {
                'content-type': 'application/json'
            }
        });
    }

    /*sends a GET request to user Web service. Receives a JSON array of all users*/
    function findAllUsers() {
        return fetch(self.url)
            .then(function (response) {
                return response.json();
            });
    }

    /*
    sends a GET request to user Web service with userId as path parameter.
    Receives a single JSON object for the userId
    */
    function findUserById(userId, callback) {
        return fetch(self.url + '/' + userId)
            .then(function(response){
                return response.json();
            });
    }

    /*
     accepts a user id and user object with new property values for the user with user id.
     Sends PUT request with user object and user id as path parameter
     */
    function updateUser(userId, user, callback) {
        return fetch(self.url + '/' + userId, {
            method: 'put',
            body: JSON.stringify(user),
            headers: {
                'content-type': 'application/json'
            }
        })
            .then(function (response) {
                if (response.bodyUsed) {
                    return response.json();
                }
                else {
                    return null;
                }
            });
    }

    /*
    sends a DELETE request to user Web service with user id as path parameter for user to remove. Receives status
    */
    function deleteUser(userId, callback) {
        return fetch(self.url + '/' + userId, {
            method: 'delete'
        })
    }

    /*
    To register a user if the user is valid
     */
    function register(user) {
        return fetch(self.registerUrl, {
            credentials: 'same-origin',
            method: 'post',
            body: JSON.stringify(user),
            headers: {
                'content-type': 'application/json'
            }
        }).then (function (response) {
            return response;
        });
    }

    /*
    posts credentials including username and password to a user web service.
     */
    function login(user) {
        return fetch(self.loginUrl, {
            credentials: 'same-origin',
            method: 'post',
            body: JSON.stringify(user),
            headers: {
                'content-type': 'application/json'
            }
        }).then (function (response) {
            return response;
        });

    }

    function getInfo(){
        return fetch(self.profile,{
            credentials: 'same-origin',
            headers: {
                'content-type': 'application/json'
            }
        }).then(function (response) {
            return response.json().then(function (result) {
                return result;
            })
        });
    }

}
